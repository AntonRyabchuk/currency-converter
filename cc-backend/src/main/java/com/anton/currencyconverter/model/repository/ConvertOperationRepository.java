package com.anton.currencyconverter.model.repository;

import com.anton.currencyconverter.model.ConvertOperation;
import com.anton.currencyconverter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConvertOperationRepository extends JpaRepository<ConvertOperation, Long> {

    List<ConvertOperation> findByUserAndDate(User user, Date date);
}
