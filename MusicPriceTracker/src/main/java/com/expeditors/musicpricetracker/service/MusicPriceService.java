package com.expeditors.musicpricetracker.service;

import com.expeditors.musicpricetracker.model.Price;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MusicPriceService {

    private List<Price> prices = List.of(
            new Price(233,34.5,new Date("2010/02/23")),
            new Price(111,45.99,new Date("2010/02/23")),
            new Price(443,456.56,new Date("2023/06/13"))
    );

    public Optional<Price> getTrackPrice(int id) {
        Optional<Price> price = prices.stream().filter(f -> f.getId() == id).findFirst();
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);

        price.ifPresent(price1 -> price1.setPrice(Double.parseDouble(nf.format(ThreadLocalRandom.current().nextDouble(1, 300)))));
        return price;
    }

}
