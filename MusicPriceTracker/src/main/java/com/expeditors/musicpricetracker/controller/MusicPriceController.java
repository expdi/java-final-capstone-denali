package com.expeditors.musicpricetracker.controller;

import com.expeditors.musicpricetracker.model.Price;
import com.expeditors.musicpricetracker.model.PriceLimit;
import com.expeditors.musicpricetracker.service.MusicPriceService;
import com.expeditors.musicpricetracker.service.PriceLimitService;
import com.expeditors.musictracking.dto.CustomResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/MusicTrackPrice")
public class MusicPriceController {

    @Autowired
    private MusicPriceService service;

    @GetMapping("/{id}")
    public Optional<Price> getPriceById(@PathVariable int id) {

        return service.getTrackPrice(id);
    }


    @PutMapping("/priceLimit")
    public ResponseEntity<?> updateEntity(@RequestBody PriceLimit priceLimit) {
        boolean result = service.update(priceLimit);

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }


}
