package ru.tinkoff.trade.grpcclient.service;

import static ru.tinkoff.piapi.contract.v1.SubscriptionAction.SUBSCRIPTION_ACTION_SUBSCRIBE;
import static ru.tinkoff.piapi.contract.v1.SubscriptionAction.SUBSCRIPTION_ACTION_UNSUBSCRIBE;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInstrument;
import ru.tinkoff.piapi.contract.v1.MarketDataResponse;
import ru.tinkoff.piapi.contract.v1.MarketDataServerSideStreamRequest;
import ru.tinkoff.piapi.contract.v1.MarketDataStreamServiceGrpc.MarketDataStreamServiceBlockingStub;
import ru.tinkoff.piapi.contract.v1.OrderBookInstrument;
import ru.tinkoff.piapi.contract.v1.SubscribeCandlesRequest;
import ru.tinkoff.piapi.contract.v1.SubscribeOrderBookRequest;
import ru.tinkoff.piapi.contract.v1.SubscriptionInterval;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketDataStreamServiceClient {

  private final MarketDataStreamServiceBlockingStub stub;


  public Iterator<MarketDataResponse> startSubscribeCandles(Set<String> figiSet,
      SubscriptionInterval interval) {

    if (figiSet == null || figiSet.isEmpty()) {
      log.info("Figi set is empty");
      return Collections.emptyIterator();
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

    return stub.marketDataServerSideStream(request);
  }

  public Iterator<MarketDataResponse> startSubscribeOrderBook(String figi) {

    if (StringUtils.isBlank(figi)) {
      log.info("Figi set is empty");
      return Collections.emptyIterator();
    }

    var request = MarketDataServerSideStreamRequest.newBuilder()
        .setSubscribeOrderBookRequest(SubscribeOrderBookRequest.newBuilder()
            .setSubscriptionAction(SUBSCRIPTION_ACTION_SUBSCRIBE)
            .addAllInstruments(Stream.of(figi).map(figiValue -> OrderBookInstrument.newBuilder()
                .setDepth(50)
                .setInstrumentId(figiValue)
                .build()).collect(Collectors.toSet()))
            .build())
        .build();

    return stub.marketDataServerSideStream(request);
  }

  public void stopSubscribeCandles(Set<String> figiSet,
      SubscriptionInterval interval) {

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

    stub.marketDataServerSideStream(request);
  }

  public void stopSubscribeOrderBook(String figi) {

    var request = MarketDataServerSideStreamRequest.newBuilder()
        .setSubscribeOrderBookRequest(SubscribeOrderBookRequest.newBuilder()
            .setSubscriptionAction(SUBSCRIPTION_ACTION_UNSUBSCRIBE)
            .addAllInstruments(Stream.of(figi).map(figiValue -> OrderBookInstrument.newBuilder()
                .setDepth(50)
                .setInstrumentId(figiValue)
                .build()).collect(Collectors.toSet()))
            .build())
        .build();

    stub.marketDataServerSideStream(request);
  }


}
