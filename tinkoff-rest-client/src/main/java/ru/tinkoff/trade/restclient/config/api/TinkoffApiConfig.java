package ru.tinkoff.trade.restclient.config.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.trade.restclient.invest.api.InstrumentsServiceApi;
import ru.tinkoff.trade.restclient.invest.api.MarketDataServiceApi;
import ru.tinkoff.trade.restclient.invest.api.MarketDataStreamServiceApi;
import ru.tinkoff.trade.restclient.invest.api.OperationsServiceApi;
import ru.tinkoff.trade.restclient.invest.api.OperationsStreamServiceApi;
import ru.tinkoff.trade.restclient.invest.api.OrdersServiceApi;
import ru.tinkoff.trade.restclient.invest.api.OrdersStreamServiceApi;
import ru.tinkoff.trade.restclient.invest.api.SandboxServiceApi;
import ru.tinkoff.trade.restclient.invest.api.StopOrdersServiceApi;
import ru.tinkoff.trade.restclient.invest.api.UsersServiceApi;
import ru.tinkoff.trade.restclient.property.TinkoffApiProperty;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TinkoffApiConfig {

  private final TinkoffApiProperty tinkoffApiProperty;


  @Bean(name = "instrumentsClientApi")
  public InstrumentsServiceApi instrumentsClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new InstrumentsServiceApi(apiClient);
  }

  @Bean(name = "marketDataClientApi")
  public MarketDataServiceApi marketDataClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new MarketDataServiceApi(apiClient);
  }

  @Bean(name = "marketDataStreamClientApi")
  public MarketDataStreamServiceApi marketDataStreamClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new MarketDataStreamServiceApi(apiClient);
  }

  @Bean(name = "operationsClientApi")
  public OperationsServiceApi operationsClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new OperationsServiceApi(apiClient);
  }

  @Bean(name = "operationsStreamClientApi")
  public OperationsStreamServiceApi operationsStreamClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new OperationsStreamServiceApi(apiClient);
  }

  @Bean(name = "ordersClientApi")
  public OrdersServiceApi ordersClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new OrdersServiceApi(apiClient);
  }

  @Bean(name = "ordersStreamClientApi")
  public OrdersStreamServiceApi ordersStreamClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new OrdersStreamServiceApi(apiClient);
  }

  @Bean(name = "sandboxClientApi")
  public SandboxServiceApi sandboxClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new SandboxServiceApi(apiClient);
  }

  @Bean(name = "stopOrdersClientApi")
  public StopOrdersServiceApi stopOrdersClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new StopOrdersServiceApi(apiClient);
  }

  @Bean(name = "usersClientApi")
  public UsersServiceApi usersClientApi(
      @Qualifier("tinkoffApiRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ru.tinkoff.trade.restclient.invest.ApiClient apiClient = new ru.tinkoff.trade.restclient.invest.ApiClient(
        tinkoffApiRestTemplate);
    apiClient.setBearerToken(tinkoffApiProperty.getToken());
    apiClient.setBasePath(tinkoffApiProperty.getBasePath());

    return new UsersServiceApi(apiClient);
  }

}
