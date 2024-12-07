package ru.tinkoff.trade.telegrambot.config;

import static java.util.Collections.singletonList;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.trade.telegrambot.property.RestProperties;
import ru.tinkoff.trade.telegrambot.property.TelegramBotApiProperty;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "telegrambot.client.enabled",
    havingValue = "true",
    matchIfMissing = true)
@ComponentScan(basePackages = "ru.tinkoff.trade.telegrambot")
public class RestTemplateConfig {

  @SneakyThrows
  @Bean("telegramBotRestTemplate")
  public RestTemplate tinkoffApiRestTemplate(TelegramBotApiProperty telegramBotApiProperty) {
    RestTemplate restTemplate = prepareBaseRestTemplate(telegramBotApiProperty.getRest());
    restTemplate.setMessageConverters(singletonList(prepareSpecificMessageConverter()));
    return restTemplate;
  }

  private RestTemplate prepareBaseRestTemplate(RestProperties restProperties)
      throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
        .setMaxConnTotal(restProperties.getMaxTotalConn())
        .setMaxConnPerRoute(restProperties.getDefaultMaxConnPerRoute())
        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(SSLContextBuilder.create()
                .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                .build())
            .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build())
        .build();

    RequestConfig requestConfig = RequestConfig
        .custom()
        .setConnectionRequestTimeout(
            Timeout.ofMilliseconds(restProperties.getConnRequestTimeoutMillis()))
        .setResponseTimeout(Timeout.ofMilliseconds(restProperties.getConnResponseTimeoutMillis()))
        .build();

    HttpClient httpClient = HttpClientBuilder.create()
        .setConnectionManager(connectionManager)
        .setDefaultRequestConfig(requestConfig).build();

    ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
        httpClient);

    return new RestTemplate(requestFactory);
  }

  private MappingJackson2HttpMessageConverter prepareSpecificMessageConverter() {
    var objectMapper = defaultObjectMapper();
    var converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    converter.setObjectMapper(objectMapper);
    return converter;
  }

  private ObjectMapper defaultObjectMapper() {
    var objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule()
            .addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE))
            .addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
            .addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    return objectMapper;
  }
}
