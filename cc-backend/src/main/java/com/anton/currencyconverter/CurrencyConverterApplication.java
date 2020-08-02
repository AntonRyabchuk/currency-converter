package com.anton.currencyconverter;

import com.anton.currencyconverter.service.api.CurrencyService;
import com.anton.currencyconverter.service.api.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CurrencyConverterApplication {

    private CurrencyService currencyService;
    private RateService rateService;

    @Autowired
    public CurrencyConverterApplication(CurrencyService currencyService, RateService rateService) {
        this.currencyService = currencyService;
        this.rateService = rateService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }

    @Bean
    CommandLineRunner getStartCurrencyData() {
        return args -> {
            currencyService.loadStartCurrencyData();
            rateService.loadDailyRateData();
        };
    }
}
