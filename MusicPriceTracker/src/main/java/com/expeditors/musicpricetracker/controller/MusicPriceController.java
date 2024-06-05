package com.expeditors.musicpricetracker.controller;

import com.expeditors.musicpricetracker.model.Price;
import com.expeditors.musicpricetracker.model.PriceLimit;
import com.expeditors.musicpricetracker.service.MusicPriceService;
import com.expeditors.musictracking.dto.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/MusicTrackPrice")
public class MusicPriceController {

    @Autowired
    private MusicPriceService service;

    @GetMapping("/{id}")
    public Optional<Price> getPriceById(@PathVariable int id) {

        return service.getTrackPrice(id);
    }

    @GetMapping("/PriceLimit")
    public ResponseEntity<CustomResponse<PriceLimit>> getLimits(){
        return ResponseEntity.ok(CustomResponse.ofValue(service.getPriceLimit()));

    }

    @PutMapping("/PriceLimit")
    public ResponseEntity<?> updateEntity(@RequestBody PriceLimit priceLimit) {
        boolean result = service.update(priceLimit);

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }




}
