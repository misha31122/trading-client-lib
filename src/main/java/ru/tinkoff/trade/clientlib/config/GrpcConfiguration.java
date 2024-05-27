package ru.tinkoff.trade.clientlib.config;

import io.grpc.CallCredentials;
import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc.InstrumentsServiceStub;
import ru.tinkoff.piapi.contract.v1.MarketDataServiceGrpc;
import ru.tinkoff.piapi.contract.v1.MarketDataServiceGrpc.MarketDataServiceStub;
import ru.tinkoff.piapi.contract.v1.MarketDataStreamServiceGrpc;
import ru.tinkoff.piapi.contract.v1.MarketDataStreamServiceGrpc.MarketDataStreamServiceBlockingStub;
import ru.tinkoff.piapi.contract.v1.OperationsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.OperationsServiceGrpc.OperationsServiceStub;
import ru.tinkoff.piapi.contract.v1.OperationsStreamServiceGrpc;
import ru.tinkoff.piapi.contract.v1.OperationsStreamServiceGrpc.OperationsStreamServiceStub;
import ru.tinkoff.piapi.contract.v1.OrdersServiceGrpc;
import ru.tinkoff.piapi.contract.v1.OrdersServiceGrpc.OrdersServiceStub;
import ru.tinkoff.piapi.contract.v1.OrdersStreamServiceGrpc;
import ru.tinkoff.piapi.contract.v1.OrdersStreamServiceGrpc.OrdersStreamServiceStub;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc.SandboxServiceStub;
import ru.tinkoff.piapi.contract.v1.StopOrdersServiceGrpc;
import ru.tinkoff.piapi.contract.v1.StopOrdersServiceGrpc.StopOrdersServiceStub;
import ru.tinkoff.piapi.contract.v1.UsersServiceGrpc;
import ru.tinkoff.piapi.contract.v1.UsersServiceGrpc.UsersServiceStub;

@Configuration
@ConditionalOnProperty(name = "tinkoff.api.client.enabled",
    havingValue = "true",
    matchIfMissing = true)
public class GrpcConfiguration {

  @Value("${tinkoff.token}")
  private String token;
  @Value("${tinkoff.grpc.client.host}")
  private String host;
  @Value("${tinkoff.grpc.client.port}")
  private Integer port;

  @Bean
  CallCredentials tinkoffCallCredentials() {
    return CallCredentialsHelper.bearerAuth(token);
  }

  @Bean
  ManagedChannel tinkoffChannel() {
    ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
        .trustManager(InsecureTrustManagerFactory.INSTANCE.getTrustManagers())
        .build();
    return Grpc.newChannelBuilderForAddress(host, port, credentials).build();
  }

  @Bean
  InstrumentsServiceStub instrumentsServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return InstrumentsServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  MarketDataServiceStub marketDataServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return MarketDataServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  MarketDataStreamServiceBlockingStub marketDataStreamServiceBlockingStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return MarketDataStreamServiceGrpc
        .newBlockingStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  OperationsServiceStub operationsServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return OperationsServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  OperationsStreamServiceStub operationsStreamServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return OperationsStreamServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  OrdersServiceStub ordersServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return OrdersServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  OrdersStreamServiceStub ordersStreamServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return OrdersStreamServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  SandboxServiceStub sandboxServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return SandboxServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  StopOrdersServiceStub stopOrdersServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return StopOrdersServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

  @Bean
  UsersServiceStub usersServiceStub(
      @Qualifier("tinkoffCallCredentials") CallCredentials creds,
      @Qualifier("tinkoffChannel") ManagedChannel tinkoffChannel) {
    return UsersServiceGrpc.newStub(tinkoffChannel)
        .withCallCredentials(creds);
  }

}
