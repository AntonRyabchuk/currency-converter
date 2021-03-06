package com.anton.currencyconverter.service.impl;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.entity.ConvertOperation;
import com.anton.currencyconverter.domain.entity.Rate;
import com.anton.currencyconverter.domain.entity.User;
import com.anton.currencyconverter.domain.repository.CurrencyRepository;
import com.anton.currencyconverter.domain.repository.ConvertOperationRepository;
import com.anton.currencyconverter.domain.repository.RateRepository;
import com.anton.currencyconverter.service.api.ConvertOperationService;
import com.anton.currencyconverter.service.api.RateService;
import com.anton.currencyconverter.dto.request.ConvertOperationForm;
import com.anton.currencyconverter.dto.response.ConvertOperationResponse;
import com.anton.currencyconverter.service.api.UserService;
import com.anton.currencyconverter.service.exceptions.CurrencyException;
import com.anton.currencyconverter.utils.Converters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConvertOperationServiceImpl implements ConvertOperationService {

    private final ConvertOperationRepository convertOperationRepository;
    private final CurrencyRepository currencyRepository;
    private final RateRepository rateRepository;
    private final RateService rateService;
    private final UserService userService;

    public ConvertOperationServiceImpl(ConvertOperationRepository convertOperationRepository, CurrencyRepository currencyRepository, RateRepository rateRepository, RateService rateService, UserService userService) {
        this.convertOperationRepository = convertOperationRepository;
        this.currencyRepository = currencyRepository;
        this.rateRepository = rateRepository;
        this.rateService = rateService;
        this.userService = userService;
    }

    @Override
    public Double calculateTargetValue(ConvertOperationForm form) {
        var currencyFrom = currencyRepository.findById(form.getCurrencyIdFrom()).orElseThrow(() -> new CurrencyException("No such currency found"));
        var currencyTo = currencyRepository.findById(form.getCurrencyIdTo()).orElseThrow(() -> new CurrencyException("No such currency found"));
        var today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        checkRateAvailable(currencyFrom);
        var rateFrom = rateRepository.findByCurrencyAndDate(currencyFrom, today);
        checkRateAvailable(currencyTo);
        var rateTo = rateRepository.findByCurrencyAndDate(currencyTo, today);
        return round(form.getValueFrom() * calculateResultRate(rateFrom, rateTo), 2);
    }

    @Override
    public ConvertOperationResponse createConvertOperation(User user, ConvertOperationForm form) {
        var currencyFrom = currencyRepository.findById(form.getCurrencyIdFrom()).orElseThrow(() -> new RuntimeException("No such currency found"));
        var currencyTo = currencyRepository.findById(form.getCurrencyIdTo()).orElseThrow(() -> new RuntimeException("No such currency found"));
        var today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        var rateFrom = rateRepository.findByCurrencyAndDate(currencyFrom, today);
        var rateTo = rateRepository.findByCurrencyAndDate(currencyTo, today);
        var valueTo = round(form.getValueFrom() * calculateResultRate(rateFrom, rateTo), 2);

        var operation = new ConvertOperation();
        operation.setUser(user);
        operation.setCurrencyFrom(currencyFrom);
        operation.setValueFrom(form.getValueFrom());
        operation.setCurrencyTo(currencyTo);
        operation.setValueTo(valueTo);
        operation.setDate(today);

        log.info("IN ExchangeOperationServiceImpl createExchangeOperation - exchange operation: {} was saved", operation);

        return Converters.convertToDto(convertOperationRepository.save(operation));
    }

    @Override
    public List<ConvertOperationResponse> findAllBy(String currencyIdFrom, String currencyIdTo, Date date) {
        var user = userService.getUserFromContext();
        var currencyFrom = currencyRepository.findById(currencyIdFrom).orElseThrow(() -> new RuntimeException("No such currency found"));
        var currencyTo = currencyRepository.findById(currencyIdTo).orElseThrow(() -> new RuntimeException("No such currency found"));
        var operations = convertOperationRepository.findAllByUserAndCurrencyFromAndCurrencyToAndDate(user, currencyFrom, currencyTo, date);
        log.info("IN ExchangeOperationServiceImpl findAllBy - operations: {} was found", operations);
        return operations.stream()
                .map(Converters::convertToDto)
                .collect(Collectors.toList());
    }

    private void checkRateAvailable(Currency currency) {
        var today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (!rateRepository.existsByCurrencyAndDate(currency, today)) {
            rateService.loadDailyRateData();
        }
    }

    private Double calculateResultRate(Rate rateFrom, Rate rateTo) {
        return (rateFrom.getRate() / rateFrom.getNominal()) / (rateTo.getRate() / rateTo.getNominal());
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        var bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
