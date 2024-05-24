package ru.tinkoff.trade.clientlib.service;

import static ru.tinkoff.piapi.contract.v1.SubscriptionAction.SUBSCRIPTION_ACTION_SUBSCRIBE;
import static ru.tinkoff.piapi.contract.v1.SubscriptionAction.SUBSCRIPTION_ACTION_UNSUBSCRIBE;

import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.grpc.stub.StreamObserver;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInstrument;
import ru.tinkoff.piapi.contract.v1.MarketDataResponse;
import ru.tinkoff.piapi.contract.v1.MarketDataServerSideStreamRequest;
import ru.tinkoff.piapi.contract.v1.MarketDataStreamServiceGrpc;
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

  @Value("${tinkoff.token}")
  private String token;
  @Value("${tinkoff.grpc.client.host}")
  private String host;
  @Value("${tinkoff.grpc.client.port}")
  private Integer port;
  private MarketDataStreamServiceStub stub;

  @PostConstruct
  private void postConstruct() {
    ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
        .trustManager(InsecureTrustManagerFactory.INSTANCE.getTrustManagers())
        .build();
    ManagedChannel channel = Grpc.newChannelBuilderForAddress(host, port, credentials)
        .build();

    this.stub = MarketDataStreamServiceGrpc.newStub(channel)
        .withCallCredentials(CallCredentialsHelper.bearerAuth(token));
  }


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
