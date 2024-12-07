package ru.tinkoff.trade.telegrambot.property;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class TelegramBotApiProperty {

  private RestProperties rest;
  private String token;
  private String basePath;

}
