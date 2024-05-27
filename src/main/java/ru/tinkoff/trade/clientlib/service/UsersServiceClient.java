package ru.tinkoff.trade.clientlib.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.UsersServiceGrpc.UsersServiceStub;

@Slf4j
@ConditionalOnProperty(name = "tinkoff.api.client.enabled",
    havingValue = "true",
    matchIfMissing = true)
@RequiredArgsConstructor
@Service
public class UsersServiceClient {

  private final UsersServiceStub stub;
}
