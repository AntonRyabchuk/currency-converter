package com.anton.currencyconverter.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Currency currency;

    private Integer nominal;

    private Double rate;

    private Date date;
}
