package ru.tinkoff.trade.restclient.property;

import lombok.Data;

@Data
public class RestProperties {

  private int maxTotalConn;
  private int defaultMaxConnPerRoute;
  private int connRequestTimeoutMillis;
  private int connResponseTimeoutMillis;

}
