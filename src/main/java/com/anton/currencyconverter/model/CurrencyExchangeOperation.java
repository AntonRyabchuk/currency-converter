package com.anton.currencyconverter.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class CurrencyExchangeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Currency currencyFrom;

    @ManyToOne
    private Currency currencyTo;

    private Date date;
}
