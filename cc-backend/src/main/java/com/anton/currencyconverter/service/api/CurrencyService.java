package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.domain.entity.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> loadStartCurrencyData();
}
