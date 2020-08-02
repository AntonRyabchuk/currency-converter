package com.anton.currencyconverter.dto.request;

import com.anton.currencyconverter.domain.entity.User;
import lombok.Data;

@Data
public class ConvertOperationForm {

    private User user;

    private String currencyIdFrom;

    private Double valueFrom;

    private String currencyIdTo;
}
