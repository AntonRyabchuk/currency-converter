package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.model.Rate;

import java.util.List;

public interface RateService {

    List<Rate> loadDailyRateData();
}
