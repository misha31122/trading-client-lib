package ru.tinkoff.trade.clientlib;

import static ru.tinkoff.piapi.contract.v1.SubscriptionAction.SUBSCRIPTION_ACTION_SUBSCRIBE;
import static ru.tinkoff.piapi.contract.v1.SubscriptionInterval.SUBSCRIPTION_INTERVAL_ONE_HOUR;

import io.grpc.StatusRuntimeException;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInstrument;
import ru.tinkoff.piapi.contract.v1.MarketDataResponse;
import ru.tinkoff.piapi.contract.v1.MarketDataServerSideStreamRequest;
import ru.tinkoff.piapi.contract.v1.MarketDataStreamServiceGrpc;
import ru.tinkoff.piapi.contract.v1.SubscribeCandlesRequest;
import ru.tinkoff.trade.clientlib.service.CandleDataService;

@Slf4j
@ConditionalOnProperty(name = "tinkoff.api.client.enabled", havingValue = "true")
@RequiredArgsConstructor
@Service
public class MarketDataStreamServiceClient {

  private final CandleDataService candleDataService;

  @GrpcClient("market-data-stream-blocking-stub-client")
  private MarketDataStreamServiceGrpc.MarketDataStreamServiceBlockingStub marketDataBlockingStub;

  public void getCandlesStreamData(Set<String> figiSet) {

    if (figiSet == null || figiSet.isEmpty()) {
      log.info("Figi set is empty");
      return;
    }

    var request = MarketDataServerSideStreamRequest.newBuilder()
        .setSubscribeCandlesRequest(SubscribeCandlesRequest.newBuilder()
            .setSubscriptionAction(SUBSCRIPTION_ACTION_SUBSCRIBE)
            .addAllInstruments(figiSet.stream().map(figi -> CandleInstrument.newBuilder()
                    .setInterval(SUBSCRIPTION_INTERVAL_ONE_HOUR)
                    .setInstrumentId(figi)
                    .build()).collect(Collectors.toSet()))
            .build())
        .build();

    Iterator<MarketDataResponse> marketDataResponses;

    try {
      marketDataResponses = marketDataBlockingStub.marketDataServerSideStream(request);
      while (marketDataResponses.hasNext()) {
        MarketDataResponse marketDataResponse = marketDataResponses.next();
      }
    } catch (StatusRuntimeException e) {
      log.info("RPC failed: {}", e.getStatus());
    }




  }


}
