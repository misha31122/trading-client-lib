package ru.tinkoff.trade.clientlib.service;

import ru.tinkoff.piapi.contract.v1.MarketDataResponse;

public interface CandleDataService {

  void processHourCandles(MarketDataResponse marketDataResponse);

}
