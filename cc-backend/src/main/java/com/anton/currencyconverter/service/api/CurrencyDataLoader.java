package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.model.Currency;
import com.anton.currencyconverter.model.Rate;

import java.util.List;

public interface CurrencyDataLoader {

    List<Currency> getStartCurrencyData();

    List<Rate> getTodayRateData();
}
