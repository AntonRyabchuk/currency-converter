package com.anton.currencyconverter.domain.repository;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findByCurrencyAndDate(Currency currency, Date date);

    boolean existsByCurrencyAndDate(Currency currency, Date date);
}
