package com.anton.currencyconverter.model.repository;

import com.anton.currencyconverter.model.Currency;
import com.anton.currencyconverter.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findByCurrencyAndDate(Currency currency, Date date);

    boolean existsByCurrencyAndDate(Currency currency, Date date);
}
