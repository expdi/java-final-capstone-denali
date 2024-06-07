package com.expeditors.musictracking.service.jpa;

import com.expeditors.musictracking.dao.ArtistBaseDAO;
import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.model.enumerator.Role;
import com.expeditors.musictracking.service.ArtistService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles({"jpa"})
@Transactional
public class JPAArtistServiceTest {

    @Autowired
    private JPAArtistService service;

    @Autowired
    private JPATrackService trackService;

    @BeforeEach
    @Transactional
    public void getReady() throws Exception {

        List<Artist> artists = List.of(
                new Artist(
                        3,
                        "Michel Jackson",
                        1.60,
                        LocalDate.parse("1950/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        "Texas",
                        Genre.Pop,
                        Role.Singer),
                new Artist(
                        34,
                        "Cristina Aguilera",
                        1.89,
                        LocalDate.parse("1970/02/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        "Arizona",
                        Genre.Pop,
                        Role.Singer));

        List<Track> tracks = List.of(
                new Track(
                        1,
                        213,
                        "Love",
                        "My Love is the end",
                        List.of(new Artist(
                                        1,
                                        "Michel Jackson",
                                        1.60,
                                        LocalDate.parse("1950/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                                        "Texas",
                                        Genre.Pop,
                                        Role.Singer),
                                new Artist(
                                        2,
                                        "Cristina Aguilera",
                                        1.89,
                                        LocalDate.parse("1970/02/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                                        "Arizona",
                                        Genre.Pop,
                                        Role.Singer)),
                        LocalDate.parse("2000/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        3.45,
                        Genre.Pop,
                        MediaType.MP3,
                        10.40),
                new Track(
                        5,
                        213,
                        "Memories",
                        "World is fine",
                        List.of(new Artist(
                                        33,
                                        "Michel Jackson",
                                        1.60,
                                        LocalDate.parse("1950/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                                        "Texas",
                                        Genre.Pop,
                                        Role.Singer),
                                new Artist(
                                        24,
                                        "Cristina Aguilera",
                                        1.89,
                                        LocalDate.parse("1970/02/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                                        "Arizona",
                                        Genre.Pop,
                                        Role.Singer)),
                        LocalDate.parse("1981/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        1.20,
                        Genre.Pop,
                        MediaType.Ogg,
                        10.40),
                new Track(
                        213,
                        "Disco Majul",
                        "Colorama",
                        LocalDate.parse("2029/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        new Artist(
                                20,
                                "Gativideo",
                                0,
                                LocalDate.parse("2010/02/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                                "Spain",
                                Genre.Pop,
                                Role.Producer),
                        10.40));
        tracks.forEach(trackService::insert);
        artists.forEach(service::insert);
    }


    @Test
    @Transactional
    public void serviceInsert() {

        Artist artist = new Artist(
                20,
                "Gativideo",
                0,
                LocalDate.parse("2010/02/23", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                "Spain",
                Genre.Pop,
                Role.Producer);

        Artist newArtist = service.insert(artist);

        assertEquals(newArtist,service.getById(newArtist.getArtistId()));
    }

    @Test
    @Transactional
    public void serviceUpdate() {
        Artist artist = service.getAll().get(0);

        artist.setName("Manuel Turiso");


        service.update(artist);

        assertEquals("Manuel Turiso",service.getById(artist.getArtistId()).getName());

        service.deleteById(artist.getArtistId());
    }

    @Test
    @Transactional
    public void serviceDelete() {
        Artist artist = service.getAll().get(0);

        assertTrue(service.deleteById(artist.getArtistId()));
    }


    @Test
    public void serviceSearch() {

        Artist artist = service.getAll().get(0);

        assertNull(service.getById(100));

        assertTrue(service.getByName(artist.getName()).size() > 0);

        assertTrue(service.getByRole(Role.Singer).size() > 0);
        assertFalse(service.getTracksByArtist(artist.getName()).isEmpty());
        assertFalse(service.getByMusiscGenre(Genre.Pop).isEmpty());
    }
}
