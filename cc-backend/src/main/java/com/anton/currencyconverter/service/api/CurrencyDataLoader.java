package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.entity.Rate;

import java.util.List;

public interface CurrencyDataLoader {

    List<Currency> getStartCurrencyData();

    List<Rate> getTodayRateData();
}
