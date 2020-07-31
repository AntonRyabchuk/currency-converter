package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.model.Currency;

import java.util.List;

public interface CurrencyDataLoader {

    List<Currency> getTodayCurrencyRateData();
}
