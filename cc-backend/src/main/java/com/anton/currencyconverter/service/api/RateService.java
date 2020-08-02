package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.domain.entity.Rate;

import java.util.List;

public interface RateService {

    List<Rate> loadDailyRateData();
}
