package ru.tinkoff.trade.clientlib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc;

@Slf4j
@ConditionalOnProperty(name = "tinkoff.api.client.enabled", havingValue = "true")
@RequiredArgsConstructor
@Service
public class InstrumentServiceClient {

  @GrpcClient("instrument-blocking-stub-client")
  private InstrumentsServiceGrpc.InstrumentsServiceBlockingStub instrumentsServiceBlockingStub;


}
