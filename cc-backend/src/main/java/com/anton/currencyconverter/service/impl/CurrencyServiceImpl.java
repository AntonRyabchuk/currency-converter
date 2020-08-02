package com.anton.currencyconverter.service.impl;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.repository.CurrencyRepository;
import com.anton.currencyconverter.service.api.CurrencyDataLoader;
import com.anton.currencyconverter.service.api.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyDataLoader currencyDataLoader;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyDataLoader currencyDataLoader, CurrencyRepository currencyRepository) {
        this.currencyDataLoader = currencyDataLoader;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> loadStartCurrencyData() {
        var currencies = currencyDataLoader.getStartCurrencyData();
        log.info("IN CurrencyServiceImpl getDailyCurrencyRateData - currency data: {} was loaded", currencies);
        return currencyRepository.saveAll(currencies);
    }
}
