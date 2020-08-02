package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.model.User;
import com.anton.currencyconverter.service.dto.request.ConverOperationForm;
import com.anton.currencyconverter.service.dto.response.ConvertOperationResponse;

import java.util.Date;
import java.util.List;

public interface ConvertOperationService {

    Double calculateTargetValue(ConverOperationForm form);

    ConvertOperationResponse createExchangeOperation(User user, ConverOperationForm form);

    List<ConvertOperationResponse> findOperationByDate(User user, Date date);
}
