package com.expeditors.musicpricetracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    int id;
    double price;
    LocalDate priceDate;

    public Price(int id, LocalDate priceDate) {
        this.id = id;
        this.priceDate = priceDate;
    }
}
