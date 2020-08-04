package com.anton.currencyconverter.rest;

import com.anton.currencyconverter.dto.request.ConvertOperationForm;
import com.anton.currencyconverter.dto.response.ConvertOperationResponse;
import com.anton.currencyconverter.service.api.ConvertOperationService;
import com.anton.currencyconverter.service.api.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("convert")
public class ConvertOperationController {

    private final ConvertOperationService convertOperationService;
    private final UserService userService;

    public ConvertOperationController(ConvertOperationService convertOperationService, UserService userService) {
        this.convertOperationService = convertOperationService;
        this.userService = userService;
    }

    @RequestMapping(value = "calc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public double getConvertResult(@RequestParam(required = true) String currencyIdFrom,
                                   @RequestParam(required = true) String currencyIdTo,
                                   @RequestParam(required = true) Double valueFrom
                                   ) {
        var form = new ConvertOperationForm();
        form.setCurrencyIdFrom(currencyIdFrom);
        form.setCurrencyIdTo(currencyIdTo);
        form.setValueFrom(valueFrom);
        return convertOperationService.calculateTargetValue(form);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ConvertOperationResponse createConvertResult(@RequestBody ConvertOperationForm form) {
        var user = userService.getUsernameFromContext();
        return convertOperationService.createConvertOperation(user, form);
    }
}
