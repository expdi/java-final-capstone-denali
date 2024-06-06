package com.expeditors.musicpricetracker.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceLimit {
    private double maximumPrice;
    private double minimumPrice;

}
