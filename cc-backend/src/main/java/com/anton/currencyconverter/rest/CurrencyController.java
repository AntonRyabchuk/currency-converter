package com.anton.currencyconverter.rest;

import com.anton.currencyconverter.dto.response.CurrencyResponse;
import com.anton.currencyconverter.service.api.CurrencyService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "get-all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CurrencyResponse> getAll() {
        return currencyService.findAll();
    }
}
