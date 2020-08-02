package com.anton.currencyconverter.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ConvertOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Currency currencyFrom;

    private Double valueFrom;

    @ManyToOne
    private Currency currencyTo;

    private Double valueTo;

    private Date date;
}
