package com.expeditors.musictracking.controller;

import com.expeditors.musictracking.dto.CustomResponse;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.service.TrackBaseService;
import com.expeditors.musictracking.service.TrackService;
import com.expeditors.musictracking.utils.UriCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Tracks")
public class TrackController {

    @Autowired
    private TrackBaseService service;

    @Autowired
    private UriCreator uriCreator;

    @GetMapping
    public ResponseEntity<?> getAllTracks() {
        List<Track> tracks = service.getAll();
        if(tracks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofInfo("Cannot found any track"));
        }
        return ResponseEntity.ok(CustomResponse.ofValue(tracks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Track result = service.getById(id);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found the track with id: " + id));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<?> getByName(@PathVariable("title") String title) {
        List<Track> result = service.getByTitle(title);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track with the title: " + title));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByAlbum/{album}")
    public ResponseEntity<?> getByAlbum(@PathVariable("album") String album) {
        List<Track> result = service.getByAlbum(album);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track with the album name: " + album));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByArtist/{artist}")
    public ResponseEntity<?> getByArtist(@PathVariable("artist") String artist) {
        List<Track> result = service.getByArtist(artist);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track with the artist: " + artist));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByYear/{year}")
    public ResponseEntity<?> getByYear(@PathVariable("year") int year) {
        List<Track> result = service.getByYear(year);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track from the year: " + year));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByMediaType/{mediaType}")
    public ResponseEntity<?> getByMediaType(@PathVariable("mediaType") MediaType mediaType) {
        List<Track> result = service.getByMediaType(mediaType);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track with this media type: " + mediaType));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @GetMapping("/getByDuration/{duration}/{filter}")
    public ResponseEntity<?> getByDuration(@PathVariable("duration") double duration, @Validated @PathVariable("filter") Filters filter) {
        List<Track> result = service.getByDuration(duration,filter);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track with this duration: " + duration));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @PostMapping
    public ResponseEntity<?> addTrack(@RequestBody @Valid Track track) {
        Track newTrack = service.insert(track);

        URI uri = uriCreator.getURI(newTrack.getTrackId());

        return ResponseEntity.created(uri).body(CustomResponse.ofValue(newTrack));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deteleTrack(@PathVariable("id") int id) {
        boolean result = service.deleteById(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.ofError("Cannot found any track with id: " + id));
        }

        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }

    @PutMapping
    public ResponseEntity<?> updateTrack(@RequestBody @Valid Track track) {
        boolean result = service.update(track);
        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CustomResponse.ofError("Cannot find any track with id: " + track.getTrackId()));
        }
        return ResponseEntity.ok(CustomResponse.ofValue(result));
    }
}
