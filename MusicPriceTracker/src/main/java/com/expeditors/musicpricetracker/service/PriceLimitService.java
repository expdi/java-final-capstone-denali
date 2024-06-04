package com.expeditors.musicpricetracker.service;

import com.expeditors.musicpricetracker.model.Price;
import com.expeditors.musicpricetracker.model.PriceLimit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceLimitService {


    private double maximumPrice = 120;
    private double minimunPrice = 60;
    public PriceLimit getPriceLimit(){
        return new PriceLimit(maximumPrice,minimunPrice);
    }

    public boolean update(PriceLimit priceLimit){
        this.maximumPrice = priceLimit.getMaximumPrice();
        this.minimunPrice = priceLimit.getMinimumPrice();
        return true;
    }
}
