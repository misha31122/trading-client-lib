package ru.tinkoff.trade.clientlib.service;

import static ru.tinkoff.piapi.contract.v1.SubscriptionAction.SUBSCRIPTION_ACTION_SUBSCRIBE;
import static ru.tinkoff.piapi.contract.v1.SubscriptionAction.SUBSCRIPTION_ACTION_UNSUBSCRIBE;

import io.grpc.stub.StreamObserver;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInstrument;
import ru.tinkoff.piapi.contract.v1.MarketDataResponse;
import ru.tinkoff.piapi.contract.v1.MarketDataServerSideStreamRequest;
import ru.tinkoff.piapi.contract.v1.MarketDataStreamServiceGrpc.MarketDataStreamServiceStub;
import ru.tinkoff.piapi.contract.v1.SubscribeCandlesRequest;
import ru.tinkoff.piapi.contract.v1.SubscriptionInterval;

@Slf4j
@ConditionalOnProperty(name = "tinkoff.api.client.enabled",
    havingValue = "true",
    matchIfMissing = true)
@RequiredArgsConstructor
@Service
public class MarketDataStreamServiceClient {

  private final MarketDataStreamServiceStub stub;


  public void startGetCandlesStreamData(Set<String> figiSet,
      SubscriptionInterval interval,
      StreamObserver<MarketDataResponse> streamObserver) {

    if (figiSet == null || figiSet.isEmpty()) {
      log.info("Figi set is empty");
      return;
    }

    var request = MarketDataServerSideStreamRequest.newBuilder()
        .setSubscribeCandlesRequest(SubscribeCandlesRequest.newBuilder()
            .setSubscriptionAction(SUBSCRIPTION_ACTION_SUBSCRIBE)
            .addAllInstruments(figiSet.stream().map(figi -> CandleInstrument.newBuilder()
                .setInterval(interval)
                .setInstrumentId(figi)
                .build()).collect(Collectors.toSet()))
            .setWaitingClose(true)
            .build())
        .build();

    stub.marketDataServerSideStream(request, streamObserver);
  }

  public void stopGetCandlesStreamData(Set<String> figiSet,
      SubscriptionInterval interval,
      StreamObserver<MarketDataResponse> streamObserver) {

    var request = MarketDataServerSideStreamRequest.newBuilder()
        .setSubscribeCandlesRequest(SubscribeCandlesRequest.newBuilder()
            .setSubscriptionAction(SUBSCRIPTION_ACTION_UNSUBSCRIBE)
            .addAllInstruments(figiSet.stream().map(figi -> CandleInstrument.newBuilder()
                .setInterval(interval)
                .setInstrumentId(figi)
                .build()).collect(Collectors.toSet()))
            .setWaitingClose(true)
            .build())
        .build();

    stub.marketDataServerSideStream(request, streamObserver);
  }


}
