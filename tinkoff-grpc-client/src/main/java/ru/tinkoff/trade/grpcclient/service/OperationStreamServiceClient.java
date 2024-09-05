package ru.tinkoff.trade.grpcclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.OperationsStreamServiceGrpc.OperationsStreamServiceStub;

@Slf4j
@RequiredArgsConstructor
@Service
public class OperationStreamServiceClient {

  private final OperationsStreamServiceStub stub;
}
