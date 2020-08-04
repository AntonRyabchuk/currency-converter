package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.dto.response.CurrencyResponse;

import java.util.List;

public interface CurrencyService {

    List<Currency> loadStartCurrencyData();

    List<CurrencyResponse> findAll();
}
