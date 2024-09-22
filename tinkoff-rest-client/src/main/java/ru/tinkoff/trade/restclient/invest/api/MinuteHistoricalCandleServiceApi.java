package ru.tinkoff.trade.restclient.invest.api;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tinkoff.trade.restclient.property.TinkoffApiProperty;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinuteHistoricalCandleServiceApi {

  private final TinkoffApiProperty property;

  public Resource getMinuteHistoricalCandleZipArchiveByFigi(String figi)
      throws RestClientException {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/zip");
    headers.set("Authorization", "Bearer " + property.getToken());
    HttpEntity request = new HttpEntity(headers);

    String url = UriComponentsBuilder.fromUriString(property.getBasePath() + "/history-data")
        .queryParam("figi", figi)
        .queryParam("year", LocalDate.now(ZoneId.of("Europe/Moscow")).getYear())
        .build()
        .toUriString();

    try {
      ResponseEntity<Resource> response = restTemplate.exchange(url, HttpMethod.GET, request,
          Resource.class);
      Resource zipFileContent = response.getBody();
      log.info("Successfully get resource");
      return zipFileContent;
    } catch (RestClientException e) {
      log.error("Can not get response from server {}", property.getBasePath());
      throw e;
    }
  }

}
