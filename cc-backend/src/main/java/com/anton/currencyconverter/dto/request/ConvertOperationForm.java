package com.anton.currencyconverter.dto.request;

import lombok.Data;

@Data
public class ConvertOperationForm {

    private String currencyIdFrom;

    private Double valueFrom;

    private String currencyIdTo;
}
