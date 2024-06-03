package com.expeditors.musictracking.provider;

import com.expeditors.musictracking.dto.Price;
import com.expeditors.musictracking.model.Track;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class PriceProvider {
    private String priceUrl;

    private RestClient restClient;


    public PriceProvider() {
        var baseUrl = "http://localhost:8181";
        var rootUrl = "/MusicTrackPrice";
        priceUrl = rootUrl + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public void getTrackPrice(Track track) {
        ResponseEntity<Price> result =  restClient.get()
                .uri(priceUrl, track.getTrackIdentifier())
                .retrieve()
                .toEntity(Price.class);

        Optional<Price> price = Optional.ofNullable(result.getBody());

        track.setLastPrice(price.map(Price::getPrice).orElse(0.0));
    }


}
