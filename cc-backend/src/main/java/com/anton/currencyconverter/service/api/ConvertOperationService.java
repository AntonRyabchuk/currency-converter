package com.anton.currencyconverter.service.api;

import com.anton.currencyconverter.domain.entity.User;
import com.anton.currencyconverter.dto.request.ConvertOperationForm;
import com.anton.currencyconverter.dto.response.ConvertOperationResponse;

import java.util.Date;
import java.util.List;

public interface ConvertOperationService {

    Double calculateTargetValue(ConvertOperationForm form);

    ConvertOperationResponse createConvertOperation(User user, ConvertOperationForm form);

    List<ConvertOperationResponse> findAllBy(String currencyIdFom, String currencyIdTo, Date date);
}
