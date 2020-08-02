package com.anton.currencyconverter.service.impl;

import com.anton.currencyconverter.domain.entity.Rate;
import com.anton.currencyconverter.domain.repository.RateRepository;
import com.anton.currencyconverter.service.api.CurrencyDataLoader;
import com.anton.currencyconverter.service.api.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RateServiceImpl implements RateService {

    private final CurrencyDataLoader currencyDataLoader;
    private final RateRepository rateRepository;

    public RateServiceImpl(CurrencyDataLoader currencyDataLoader, RateRepository rateRepository) {
        this.currencyDataLoader = currencyDataLoader;
        this.rateRepository = rateRepository;
    }

    @Override
    public List<Rate> loadDailyRateData() {
        var rates = currencyDataLoader.getTodayRateData();
        log.info("IN RateServiceImpl loadStartRateData - currency rate data: {} was loaded", rates);
        return rateRepository.saveAll(rates);
    }
}
