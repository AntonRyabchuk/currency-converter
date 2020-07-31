package com.anton.currencyconverter.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "currency")
public class Currency {

    @Id
    private String id;

    private Integer numCode;

    private String charCode;

    private String name;

    private Double value;

    private LocalDate updated;
}
