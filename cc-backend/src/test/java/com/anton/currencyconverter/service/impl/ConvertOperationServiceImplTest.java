package com.anton.currencyconverter.service.impl;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.entity.Rate;
import com.anton.currencyconverter.domain.repository.ConvertOperationRepository;
import com.anton.currencyconverter.domain.repository.CurrencyRepository;
import com.anton.currencyconverter.domain.repository.RateRepository;
import com.anton.currencyconverter.dto.request.ConvertOperationForm;
import com.anton.currencyconverter.service.api.ConvertOperationService;
import com.anton.currencyconverter.service.api.RateService;
import com.anton.currencyconverter.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ConvertOperationServiceImplTest {

    private ConvertOperationRepository convertOperationRepository = Mockito.mock(ConvertOperationRepository.class);
    private CurrencyRepository currencyRepository = Mockito.mock(CurrencyRepository.class);
    private RateRepository rateRepository = Mockito.mock(RateRepository.class);
    private RateService rateService = Mockito.mock(RateService.class);
    private UserService userService = Mockito.mock(UserService.class);

    private ConvertOperationService convertOperationService;

    private List<Currency> currencyData;
    private List<Rate> rateData;

    @BeforeEach
    void init() {
        convertOperationService = new ConvertOperationServiceImpl(
                this.convertOperationRepository,
                this.currencyRepository,
                this.rateRepository,
                this.rateService,
                this.userService
        );
        currencyData = List.of(
                new Currency("F001", 111, "GBP", "Фунт стерлингов"),  //номинал 1  //значение 95,6971
                new Currency("HK02", 222, "HKD", "Гонконгских долларов"),  //номинал 10  //значение 94,5495
                new Currency("US03", 333, "USD", "Доллар США"),  //номинал 1  //значение 73,2806
                new Currency("EU04", 444, "EUR", "Евро")  //номинал 10  //значение 86,6250
                );
        rateData = List.of(
                new Rate(1L, currencyData.get(0), 1, 95.6971, getTodayDate()),
                new Rate(2L, currencyData.get(1), 10, 94.5495, getTodayDate()),
                new Rate(3L, currencyData.get(2), 1, 73.2806, getTodayDate()),
                new Rate(4L, currencyData.get(3), 1, 86.6250, getTodayDate())
        );
    }

    @Test
    @DisplayName("Default case")
    void calculateTargetValueShouldReturnCorrectValue() {
        Mockito.when(currencyRepository.findById("1")).thenReturn(Optional.ofNullable(currencyData.get(0)));
        Mockito.when(currencyRepository.findById("3")).thenReturn(Optional.ofNullable(currencyData.get(2)));
        Mockito.when(rateRepository.existsByCurrencyAndDate(currencyData.get(0), getTodayDate())).thenReturn(true);
        Mockito.when(rateRepository.existsByCurrencyAndDate(currencyData.get(2), getTodayDate())).thenReturn(true);
        Mockito.when(rateRepository.findByCurrencyAndDate(currencyData.get(0), getTodayDate())).thenReturn(rateData.get(0));
        Mockito.when(rateRepository.findByCurrencyAndDate(currencyData.get(2), getTodayDate())).thenReturn(rateData.get(2));

        var form = new ConvertOperationForm("1", 10.0, "3");
        var expected = convertOperationService.calculateTargetValue(form);
        assertEquals(13.06, expected);
    }

    @Test
    @DisplayName("First nominal is greater then 1 case")
    void calculateTargetValueShouldReturnCorrectValueWhenFirstNominalIsGreaterThanOne() {
        Mockito.when(currencyRepository.findById("2")).thenReturn(Optional.ofNullable(currencyData.get(1)));
        Mockito.when(currencyRepository.findById("3")).thenReturn(Optional.ofNullable(currencyData.get(2)));
        Mockito.when(rateRepository.existsByCurrencyAndDate(currencyData.get(1), getTodayDate())).thenReturn(true);
        Mockito.when(rateRepository.existsByCurrencyAndDate(currencyData.get(2), getTodayDate())).thenReturn(true);
        Mockito.when(rateRepository.findByCurrencyAndDate(currencyData.get(1), getTodayDate())).thenReturn(rateData.get(1));
        Mockito.when(rateRepository.findByCurrencyAndDate(currencyData.get(2), getTodayDate())).thenReturn(rateData.get(2));

        var form = new ConvertOperationForm("2", 10.0, "3");
        var expected = convertOperationService.calculateTargetValue(form);
        assertEquals(1.29, expected);
    }

    @Test
    @DisplayName("Second nominal is greater then 1 case")
    void calculateTargetValueShouldReturnCorrectValueWhenSecondNominalIsGreaterThanOne() {
        Mockito.when(currencyRepository.findById("3")).thenReturn(Optional.ofNullable(currencyData.get(2)));
        Mockito.when(currencyRepository.findById("2")).thenReturn(Optional.ofNullable(currencyData.get(1)));
        Mockito.when(rateRepository.existsByCurrencyAndDate(currencyData.get(2), getTodayDate())).thenReturn(true);
        Mockito.when(rateRepository.existsByCurrencyAndDate(currencyData.get(1), getTodayDate())).thenReturn(true);
        Mockito.when(rateRepository.findByCurrencyAndDate(currencyData.get(2), getTodayDate())).thenReturn(rateData.get(2));
        Mockito.when(rateRepository.findByCurrencyAndDate(currencyData.get(1), getTodayDate())).thenReturn(rateData.get(1));

        var form = new ConvertOperationForm("3", 10.0, "2");
        var expected = convertOperationService.calculateTargetValue(form);
        assertEquals(77.51, expected);
    }

    @Test
    @DisplayName("Wrong currency id")
    void calculateTargetValueShouldTrowsCurrencyExceptionWithWrongCurrencyId() {
        Mockito.when(currencyRepository.findById("")).thenReturn(null);
        var form = new ConvertOperationForm("1", 1.0, "2");
        assertThrows(RuntimeException.class, () -> convertOperationService.calculateTargetValue(form));
    }

    private Date getTodayDate() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
