package ru.tinkoff.trade.clientlib.service;

import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc.SandboxServiceStub;

@Slf4j
@ConditionalOnProperty(name = "tinkoff.api.client.enabled",
    havingValue = "true",
    matchIfMissing = true)
@RequiredArgsConstructor
@Service
public class SandBoxServiceClient {

  @Value("${tinkoff.token}")
  private String token;
  @Value("${tinkoff.grpc.client.host}")
  private String host;
  @Value("${tinkoff.grpc.client.port}")
  private Integer port;
  private SandboxServiceStub stub;

  @PostConstruct
  private void postConstruct() {
    ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
        .trustManager(InsecureTrustManagerFactory.INSTANCE.getTrustManagers())
        .build();
    ManagedChannel channel = Grpc.newChannelBuilderForAddress(host, port, credentials)
        .build();

    this.stub = SandboxServiceGrpc.newStub(channel)
        .withCallCredentials(CallCredentialsHelper.bearerAuth(token));
  }

}
