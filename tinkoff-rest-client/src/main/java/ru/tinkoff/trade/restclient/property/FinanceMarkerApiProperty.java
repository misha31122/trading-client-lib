package ru.tinkoff.trade.restclient.property;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "tinkoff.api.rest.client.finance-marker")
public class FinanceMarkerApiProperty {

    private String basePath;
    private String apiKey;
    private RestProperties rest;
}
