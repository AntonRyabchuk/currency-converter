package com.anton.currencyconverter.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "currency")
public class Currency {

    @Id
    private String id;

    private Integer numCode;

    private String charCode;

    private String name;
}
