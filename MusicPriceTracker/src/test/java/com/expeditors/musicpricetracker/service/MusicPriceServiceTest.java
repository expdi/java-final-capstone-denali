package com.expeditors.musicpricetracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import com.expeditors.musicpricetracker.service.MusicPriceService;

@SpringBootTest
public class MusicPriceServiceTest {

    public MusicPriceService musicPriceService;

    @Test
    public void testGetTrackPriceWithoutIdException(){
        double price = musicPriceService.generatePrice();

        System.out.println(price);
    }

    @Test
    public void testIfTrackPriceAreTheSame(){

    }

    @Test
    public void testIfWithoutMaximunSetDefatul(){

    }
    @Test
    public void testIfWithoutMinimunSetDefatul(){

    }

    @Test
    public void testWithoutLimitSetDefaut(){

    }

    @Test
    public void testMaximunHighterThanMinimumAllParameter(){

    }
    @Test
    public void testMaximunHighterThanMinimumWithoutMaximumParameter(){

    }
    @Test
    public void testMinimunLowerThanMaximunWithoutMinimunParameter(){

    }
}
