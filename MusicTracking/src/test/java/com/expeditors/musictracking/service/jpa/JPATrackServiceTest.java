package com.expeditors.musictracking.service.jpa;

import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.dao.jpa.JPATrackDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.model.enumerator.Role;
import com.expeditors.musictracking.provider.PriceProvider;
import com.expeditors.musictracking.service.TrackService;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles({"jpa"})
@Transactional
public class JPATrackServiceTest {


    @Autowired
    private JPATrackService service;

    @Mock
    private PriceProvider priceProvider;

    @BeforeEach
    @Transactional
    public void getReady() throws Exception {

        List<Track> tracks = List.of(
                new Track(
                        1,
                        213,
                        "Love",
                        "My Love is the end",
                        List.of( new Artist(
                                        1,
                                        "Michel Jackson",
                                        1.60,
                                        LocalDate.parse("1970/02/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
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
                        List.of( new Artist(
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

        tracks.forEach(service::insert);
    }


    @Test
    @Transactional
    public void serviceInsert() {
        Track newtrack = new Track(
                5,
                213,
                "Memories",
                "World is fine",
                List.of( new Artist(
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
                LocalDate.parse("1981/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                1.20,
                Genre.Pop,
                MediaType.Ogg,
                10.40);

        Track insertedTrack = service.insert(newtrack);

        assertEquals(insertedTrack,service.getById(insertedTrack.getTrackId()));
    }

    @Test
    @Transactional
    public void serviceUpdate() {
        Track track = service.getAll().get(0);

        track.setTitle("Wonderful World");
        service.update(track);
        assertEquals("Wonderful World",service.getById(1).getTitle());

        service.deleteById(track.getTrackId());

        assertFalse(service.update(track));
    }

    @Test
    public void serviceDelete() {
        Track track = service.getAll().get(0);

        service.deleteById(track.getTrackId());

        assertNull(service.getById(track.getTrackId()));
    }


    @Test
    public void serviceSearch() {
        assertNull(service.getById(100));

        assertEquals(1, service.getByTitle("Disco Majul").size());
    }

    @Test
    @Transactional
    public void getTrackById() {
        Track track = service.getAll().get(0);

        assertEquals(track.getTrackId(), service.getById(track.getTrackId()).getTrackId());
    }

    @Test
    @Transactional
    public void getTracksByArtist() {
        Track track1 = new Track(
                0,
                213,
                "Love",
                "My Love is the end",
                List.of( new Artist(
                                1,
                                "Michel Jackson",
                                1.60,
                                LocalDate.parse("1950/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                                "Texas",
                                Genre.Pop,
                                Role.Singer),
                        new Artist(
                                2,
                                "Katy Perry",
                                1.89,
                                LocalDate.parse("1970/02/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                                "Arizona",
                                Genre.Pop,
                                Role.Singer)),
                LocalDate.parse("1950/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                1.20,
                Genre.Pop,
                MediaType.MP3,
                10.40);
        Track track2 =
                new Track(
                        0,
                        213,
                        "Memories",
                        "World is fine",
                        List.of( new Artist(
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
                        LocalDate.parse("1950/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        1.20,
                        Genre.Pop,
                        MediaType.MP3,
                        10.40);

        //tracks.forEach(justSpringService::insert);

        var traksOfArtists = service.getByArtist("Rider");
        assertEquals(false, traksOfArtists.size() > 0);
        // No results
        assertEquals(0, service.getByArtist("SDFsdfsdf").size());
    }

    @Test
    @Transactional
    public void getByMediaType() {

        assertFalse(service.getByMediaType(MediaType.MP3).isEmpty());
        // No results
        assertEquals( 0, service.getByMediaType(MediaType.Flac).size());
    }

    @Test
    @Transactional
    public void getByDuration() {
        assertEquals( 1, service.getByDuration(1, Filters.LessThan).size());
        assertEquals( 2, service.getByDuration(0.3, Filters.GreaterThan).size());
        assertEquals( 1, service.getByDuration(1.20, Filters.Equals).size());
        assertEquals( 3, service.getByDuration(1.20, null).size());
    }

    @Test
    @Transactional
    public void getByYear() {
        assertEquals( 1, service.getByYear(2000).size());
        assertEquals( 0, service.getByYear(1992).size());
        assertEquals( 1, service.getByYear(1981).size());
    }
}
