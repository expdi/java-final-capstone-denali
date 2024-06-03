package com.expeditors.musictracking.service;

import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.model.enumerator.Role;
import com.expeditors.musictracking.provider.PriceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class TrackTest {

    @Mock
    private TrackBaseDAO trackDao;

    @InjectMocks
    private TrackService service;

    @Autowired
    private TrackService justSpringService;

    @Mock
    private PriceProvider priceProvider;

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
                                    new Date("1950/10/02"),
                                    "Texas",
                                    Genre.Pop,
                                    Role.Singer),
                            new Artist(
                                    2,
                                    "Cristina Aguilera",
                                    1.89,
                                    new Date("1970/02/02"),
                                    "Arizona",
                                    Genre.Pop,
                                    Role.Singer)),
                    new Date("2000/10/02"),
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
                                    new Date("1950/10/02"),
                                    "Texas",
                                    Genre.Pop,
                                    Role.Singer),
                            new Artist(
                                    2,
                                    "Cristina Aguilera",
                                    1.89,
                                    new Date("1970/02/02"),
                                    "Arizona",
                                    Genre.Pop,
                                    Role.Singer)),
                    new Date("1981/10/02"),
                    1.20,
                    Genre.Pop,
                    MediaType.Ogg,
                    10.40),
            new Track(
                    213,
                    "Disco Majul",
                    "Colorama",
                    new Date("2029/10/02"),
                    new Artist(
                                    20,
                                    "Gativideo",
                                    0,
                                    new Date("2010/02/23"),
                                    "Spain",
                                    Genre.Pop,
                                    Role.Producer),
                    10.40));

    @Test
    public void serviceInsert() {

        Mockito.when(trackDao.insert(tracks.get(0))).thenReturn(tracks.get(0));
        Mockito.when(trackDao.findById(1)).thenReturn(tracks.get(0));

        Track newtrack = service.insert(tracks.get(0));

        Mockito.verify(trackDao).insert(tracks.get(0));


        assertEquals(newtrack,service.getById(1));
    }

    @Test
    public void serviceUpdate() {
        Track track = tracks.get(0);

        Mockito.when(trackDao.insert(track)).thenReturn(track);

        service.insert(track);

        Mockito.verify(trackDao).insert(track);

        track.setTitle("Wonderful World");

        Mockito.when(trackDao.update(track)).thenReturn(true);
        Mockito.when(trackDao.findById(1)).thenReturn(track);

        service.update(track);
        Mockito.verify(trackDao).update(track);
        assertEquals("Wonderful World",service.getById(1).getTitle());


        Mockito.when(trackDao.delete(1)).thenReturn(true);
        service.deleteById(1);

        Mockito.when(trackDao.update(track)).thenReturn(false);

        Mockito.verify(trackDao).delete(1);
        Mockito.verify(trackDao).update(track);

        assertFalse(service.update(track));
    }

    @Test
    public void serviceDelete() {
        Track track = tracks.get(0);

        Mockito.when(trackDao.insert(track)).thenReturn(track);
        Mockito.when(trackDao.delete(1)).thenReturn(true);
        Mockito.when(trackDao.findById(1)).thenReturn(null);

        service.insert(track);

        Mockito.verify(trackDao).insert(track);

        service.deleteById(1);

        Mockito.verify(trackDao).delete(1);

        assertNull(service.getById(1));
        Mockito.verify(trackDao).findById(1);
    }


    @Test
    public void serviceSearch() {
        Track newtrack1 = tracks.get(0);
        Mockito.when(trackDao.insert(newtrack1)).thenReturn(newtrack1);
        service.insert(newtrack1);
        Mockito.verify(trackDao).insert(newtrack1);

        Track newtrack2 = tracks.get(1);
        Mockito.when(trackDao.insert(newtrack2)).thenReturn(newtrack2);
        service.insert(newtrack2);
        Mockito.verify(trackDao).insert(newtrack2);

        Mockito.when(trackDao.findById(100)).thenReturn(null);

        assertNull(service.getById(100));
        Mockito.verify(trackDao).findById(100);

        Mockito.when(trackDao.findByTitle("Disco Majul")).thenReturn(List.of(tracks.get(2)));
        assertEquals(1, service.getByTitle("Disco Majul").size());
        Mockito.verify(trackDao).findByTitle("Disco Majul");
    }

    @Test public void getTrackById() {
        tracks.forEach(justSpringService::insert);

        var track = service.getById(1);
    }

    @Test
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
                                new Date("1950/10/02"),
                                "Texas",
                                Genre.Pop,
                                Role.Singer),
                        new Artist(
                                2,
                                "Katy Perry",
                                1.89,
                                new Date("1970/02/02"),
                                "Arizona",
                                Genre.Pop,
                                Role.Singer)),
                new Date("1950/10/02"),
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
                                new Date("1950/10/02"),
                                "Texas",
                                Genre.Pop,
                                Role.Singer),
                        new Artist(
                                2,
                                "Cristina Aguilera",
                                1.89,
                                new Date("1970/02/02"),
                                "Arizona",
                                Genre.Pop,
                                Role.Singer)),
                new Date("1950/10/02"),
                1.20,
                Genre.Pop,
                MediaType.MP3,
                10.40);

        tracks.forEach(justSpringService::insert);

        var traksOfArtists = justSpringService.getByArtist("Michel Jackson");
        assertFalse(traksOfArtists.isEmpty());
        // No results
        assertEquals(0, justSpringService.getByArtist("SDFsdfsdf").size());
    }

    @Test
    public void getByMediaType() {
        tracks.forEach(justSpringService::insert);

        assertFalse(justSpringService.getByMediaType(MediaType.MP3).isEmpty());
        // No results
        assertEquals( 0, justSpringService.getByMediaType(MediaType.Flac).size());
    }

    @Test
    public void getByDuration() {
        //tracks.forEach(justSpringService::insert);
        assertEquals( 1, justSpringService.getByDuration(1, Filters.LessThan).size());
        assertEquals( 2, justSpringService.getByDuration(0.3, Filters.GreaterThan).size());
        assertEquals( 1, justSpringService.getByDuration(1.20, Filters.Equals).size());
        assertEquals( 0, justSpringService.getByDuration(1.20, null).size());
    }

    @Test
    public void getByYear() {
        //tracks.forEach(justSpringService::insert);

        assertEquals( 1, justSpringService.getByYear(2000).size());
        assertEquals( 0, justSpringService.getByYear(1992).size());
        assertEquals( 1, justSpringService.getByYear(1981).size());
    }
}
