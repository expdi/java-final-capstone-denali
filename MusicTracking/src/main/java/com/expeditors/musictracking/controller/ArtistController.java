package com.expeditors.musictracking.controller;

import com.expeditors.musictracking.dto.CustomResponse;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import com.expeditors.musictracking.service.ArtistService;
import com.expeditors.musictracking.service.TrackService;
import com.expeditors.musictracking.utils.UriCreator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Artist")
public class ArtistController {
    @Autowired
    private ArtistService service;

    @Autowired
    private UriCreator uriCreator;

    @GetMapping
    public ResponseEntity<?> getAllArtists() {
        List<Artist> artists = service.getAll();
        if(artists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofInfo("Cannot found any artist"));
        }
        return ResponseEntity.ok(CustomResponse.ofValue(artists));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Artist result = service.getById(id);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found artist with id: " + id));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        List<Artist> result = service.getByName(name);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any artist with the name: " + name));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getTracks/{artist}")
    public ResponseEntity<?> getTracks(@PathVariable("artist") String artist) {
        List<Track> result = service.getTracksByArtist(artist);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track with the artist: " + artist));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByRole/{role}")
    public ResponseEntity<?> getByRole(@PathVariable("role") Role role) {
        List<Artist> result = service.getByRole(role);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any artist with the role: " + role));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByMusicGenre/{genre}")
    public ResponseEntity<?> getByMusicGenre(@PathVariable("genre") Genre genre) {
        List<Artist> result = service.getByMusiscGenre(genre);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any artist with the genre: " + genre));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @PostMapping
    public ResponseEntity<?> addArtist(@RequestBody @Valid Artist artist) {
        Artist newArtist = service.insert(artist);

        URI uri = uriCreator.getURI(newArtist.getId());

        return ResponseEntity.created(uri).body(CustomResponse.ofValue(newArtist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deteleArtist(@PathVariable("id") int id) {
        boolean result = service.deleteById(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found artist with id: " + id));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @PutMapping
    public ResponseEntity<?> updateEntity(@RequestBody @Valid Artist artist) {
        boolean result = service.update(artist);
        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CustomResponse.ofError("Cannot find artist with id: " + artist.getId()));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }
}
