package com.anton.currencyconverter.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ConvertOperationResponse {

    private Long id;

    private CurrencyResponse currencyFrom;

    private Double valueFrom;

    private CurrencyResponse currencyTo;

    private Double valueTo;

    private Date date;
}
