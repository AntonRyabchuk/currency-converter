package com.anton.currencyconverter.domain.repository;

import com.anton.currencyconverter.domain.entity.ConvertOperation;
import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConvertOperationRepository extends JpaRepository<ConvertOperation, Long> {

    List<ConvertOperation> findByUserAndDate(User user, Date date);

    List<ConvertOperation> findAllByUserAndCurrencyFromAndCurrencyToAndDate(User user, Currency from, Currency to, Date date);
}
