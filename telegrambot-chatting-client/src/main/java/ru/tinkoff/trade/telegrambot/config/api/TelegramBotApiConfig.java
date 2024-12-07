package ru.tinkoff.trade.telegrambot.config.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.trade.restclient.telegrambot.ApiClient;
import ru.tinkoff.trade.restclient.telegrambot.api.TelegramBotSignalServiceApi;
import ru.tinkoff.trade.telegrambot.property.TelegramBotApiProperty;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TelegramBotApiConfig {

  private final TelegramBotApiProperty telegramBotApiProperty;


  @Bean(name = "telegramBotSignalServiceApi")
  public TelegramBotSignalServiceApi telegramBotSignalServiceApi(
      @Qualifier("telegramBotRestTemplate") RestTemplate tinkoffApiRestTemplate) {
    ApiClient apiClient = new ApiClient(tinkoffApiRestTemplate);
    apiClient.setBasePath(telegramBotApiProperty.getBasePath());

    return new TelegramBotSignalServiceApi(apiClient);
  }
}
