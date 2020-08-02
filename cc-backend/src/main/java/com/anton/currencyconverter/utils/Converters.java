package com.anton.currencyconverter.utils;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.entity.ConvertOperation;
import com.anton.currencyconverter.dto.response.CurrencyResponse;
import com.anton.currencyconverter.dto.response.ConvertOperationResponse;

public class Converters {

    public static ConvertOperationResponse convertToDto(ConvertOperation convertOperation) {
        var response = new ConvertOperationResponse();
        response.setId(convertOperation.getId());
        response.setCurrencyFrom(convertToDto(convertOperation.getCurrencyFrom()));
        response.setValueFrom(convertOperation.getValueFrom());
        response.setCurrencyTo(convertToDto(convertOperation.getCurrencyTo()));
        response.setValueTo(convertOperation.getValueTo());
        response.setDate(convertOperation.getDate());
        return response;
    }

    public static CurrencyResponse convertToDto(Currency currency) {
        var response = new CurrencyResponse();
        response.setId(currency.getId());
        response.setCharCode(currency.getCharCode());
        response.setName(currency.getName());
        return response;
    }
}
