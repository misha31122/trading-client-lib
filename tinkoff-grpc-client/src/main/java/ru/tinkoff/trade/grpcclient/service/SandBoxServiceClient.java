package ru.tinkoff.trade.grpcclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc.SandboxServiceStub;

@Slf4j
@RequiredArgsConstructor
@Service
public class SandBoxServiceClient {

  private final SandboxServiceStub stub;
}
