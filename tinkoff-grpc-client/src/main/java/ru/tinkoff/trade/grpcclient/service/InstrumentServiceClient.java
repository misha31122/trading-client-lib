package ru.tinkoff.trade.grpcclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc.InstrumentsServiceStub;

@Slf4j
@RequiredArgsConstructor
@Service
public class InstrumentServiceClient {

  private final InstrumentsServiceStub stub;
}
