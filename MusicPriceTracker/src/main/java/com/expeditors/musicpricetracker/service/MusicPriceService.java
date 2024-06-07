package com.expeditors.musicpricetracker.service;

import com.expeditors.musicpricetracker.model.Price;
import com.expeditors.musicpricetracker.model.PriceLimit;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MusicPriceService {

    private double maximumPrice = 120;
    private double minimunPrice = 60;

    public PriceLimit getPriceLimit(){
        return new PriceLimit(maximumPrice,minimunPrice);
    }
    public boolean isPriceValid(PriceLimit pricelimit){

        Boolean maxPriceFilled = pricelimit.getMaximumPrice() != 0;
        Boolean minPriceFilled = pricelimit.getMinimumPrice() != 0;

        if (!maxPriceFilled){pricelimit.setMaximumPrice(maximumPrice);}
        if (!minPriceFilled){pricelimit.setMinimumPrice(minimunPrice);}

        Boolean priceIsValid = pricelimit.getMaximumPrice() > pricelimit.getMinimumPrice();


        return priceIsValid;
    }

    public boolean update(PriceLimit priceLimit){
        if (this.isPriceValid(priceLimit)) {
            this.maximumPrice = priceLimit.getMaximumPrice();
            this.minimunPrice = priceLimit.getMinimumPrice();
            return true;
        }
        return false;
    }


    public double generatePrice(){
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);
        return  Double.parseDouble(nf.format(ThreadLocalRandom.current().nextDouble(this.minimunPrice, this.maximumPrice)));
    }
    public Optional<Price> getTrackPrice(int durationSeconds) {


        Price price = new Price(
                durationSeconds, LocalDate.now()
        );

        price.setPrice(generatePrice());
        return Optional.of(price);
    }



}
