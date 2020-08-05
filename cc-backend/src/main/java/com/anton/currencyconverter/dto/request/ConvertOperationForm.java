package com.anton.currencyconverter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvertOperationForm {

    private String currencyIdFrom;

    private Double valueFrom;

    private String currencyIdTo;
}
