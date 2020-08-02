package com.anton.currencyconverter.service.dto.request;

import com.anton.currencyconverter.model.User;
import lombok.Data;

@Data
public class ConverOperationForm {

    private User user;

    private String currencyIdFrom;

    private Double valueFrom;

    private String currencyIdTo;
}
