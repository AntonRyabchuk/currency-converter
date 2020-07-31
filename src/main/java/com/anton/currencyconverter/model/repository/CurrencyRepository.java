package com.anton.currencyconverter.model.repository;

import com.anton.currencyconverter.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
