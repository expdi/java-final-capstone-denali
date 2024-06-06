package com.expeditors.musicpricetracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.expeditors.musicpricetracker.service.MusicPriceService;
import static org.junit.jupiter.api.Assertions.*;
import com.expeditors.musicpricetracker.model.PriceLimit;

@SpringBootTest
public class MusicPriceServiceTest {

    @Autowired
    public MusicPriceService musicPriceService;

    @Test
    public void testPriceIsValidWhenGivenAValidPrice(){

        PriceLimit priceLimit = new PriceLimit(1.1, 0.1);

        boolean result = musicPriceService.isPriceValid(priceLimit);

        assertTrue(result);

    }

    @Test
    public void testPriceIsInvalidWhenGivenAInvalidPrice(){

        PriceLimit priceLimit = new PriceLimit(0.1, 2.1);

        boolean result = musicPriceService.isPriceValid(priceLimit);

        assertFalse(result);

    }

    @Test
    public void testPriceValidWhenGivenOnlyMaxValue(){

        PriceLimit priceLimit = new PriceLimit(129.0, 0);

        musicPriceService.update(priceLimit);

        boolean result = musicPriceService.isPriceValid(priceLimit);

        assertTrue(result);

    }

    @Test
    public void testPriceInvalidWhenGivenOnlyMaxValue(){

        PriceLimit priceLimit = new PriceLimit(20.0, 0);

        musicPriceService.update(priceLimit);

        boolean result = musicPriceService.isPriceValid(priceLimit);

        assertFalse(result);

    }

    @Test
    public void testPriceValidWhenGivenOnlyMinValue(){

        PriceLimit priceLimit = new PriceLimit(0, 19);

        musicPriceService.update(priceLimit);

        boolean result = musicPriceService.isPriceValid(priceLimit);

        assertTrue(result);

    }

    @Test
    public void testPriceInvalidWhenGivenOnlyMinValue(){

        PriceLimit priceLimit = new PriceLimit(0, 1890);

        musicPriceService.update(priceLimit);

        boolean result = musicPriceService.isPriceValid(priceLimit);

        assertFalse(result);

    }

    @Test
    public void testPriceIsValidWhenGivenNonePrice(){

        PriceLimit priceLimit = new PriceLimit();

        musicPriceService.update(priceLimit);

        boolean result = musicPriceService.isPriceValid(priceLimit);

        assertTrue(result);

    }

}
