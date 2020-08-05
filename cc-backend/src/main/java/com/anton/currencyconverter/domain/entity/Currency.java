package com.anton.currencyconverter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    private String id;

    private Integer numCode;

    private String charCode;

    private String name;
}
