package com.expeditors.musictracking.service;

import com.expeditors.musictracking.dao.ArtistBaseDAO;
import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.model.enumerator.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles({"inmemory"})
public class ArtistTest {

    @Mock
    private ArtistBaseDAO artistDAO;

    @Mock
    private TrackBaseDAO trackDAO;

    @InjectMocks
    private ArtistService service;

    List<Artist> artists = List.of(
            new Artist(
                    0,
                    "Michel Jackson",
                    1.60,
                    LocalDate.parse("1950/10/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                    "Texas",
                    Genre.Pop,
                    Role.Singer),
            new Artist(
                    0,
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


    @Test
    public void serviceInsert() {

        Mockito.when(artistDAO.insert(artists.get(0))).thenReturn(artists.get(0));
        Mockito.when(artistDAO.findById(1)).thenReturn(artists.get(0));

        Artist newArtist = service.insert(artists.get(0));

        Mockito.verify(artistDAO).insert(artists.get(0));


        assertEquals(newArtist,service.getById(1));
    }

    @Test
    public void serviceUpdate() {
        Artist artist = artists.get(0);

        Mockito.when(artistDAO.insert(artist)).thenReturn(artist);

        service.insert(artist);

        Mockito.verify(artistDAO).insert(artist);

        artist.setName("Manuel Turiso");

        Mockito.when(artistDAO.update(artist)).thenReturn(true);
        Mockito.when(artistDAO.findById(1)).thenReturn(artist);

        service.update(artist);
        Mockito.verify(artistDAO).update(artist);
        assertEquals("Manuel Turiso",service.getById(1).getName());


        Mockito.when(artistDAO.deleteById(1)).thenReturn(true);
        service.deleteById(1);

        Mockito.when(artistDAO.update(artist)).thenReturn(false);

        Mockito.verify(artistDAO).deleteById(1);
        Mockito.verify(artistDAO).update(artist);

        assertFalse(service.update(artist));
    }

    @Test
    public void serviceDelete() {
        Artist artist = artists.get(0);

        Mockito.when(artistDAO.insert(artist)).thenReturn(artist);
        Mockito.when(artistDAO.deleteById(1)).thenReturn(true);
        Mockito.when(artistDAO.findById(1)).thenReturn(null);

        service.insert(artist);

        Mockito.verify(artistDAO).insert(artist);

        service.deleteById(1);

        Mockito.verify(artistDAO).deleteById(1);

        assertNull(service.getById(1));
        Mockito.verify(artistDAO).findById(1);
    }


    @Test
    public void serviceSearch() {

        Artist newArtist1 = artists.get(0);
        Mockito.when(artistDAO.insert(newArtist1)).thenReturn(newArtist1);
        service.insert(newArtist1);
        Mockito.verify(artistDAO).insert(newArtist1);

        Artist newArtist2 = artists.get(1);
        Mockito.when(artistDAO.insert(newArtist2)).thenReturn(newArtist2);
        service.insert(newArtist2);
        Mockito.verify(artistDAO).insert(newArtist2);

        Mockito.when(artistDAO.findById(100)).thenReturn(null);

        assertNull(service.getById(100));
        Mockito.verify(artistDAO).findById(100);

        Mockito.when(artistDAO.findByName("Michel Jackson")).thenReturn(artists);
        assertEquals(2, service.getByName("Michel Jackson").size());
        Mockito.verify(artistDAO).findByName("Michel Jackson");

        Mockito.when(artistDAO.findByMusicGenre(Genre.Pop)).thenReturn(artists);
        assertEquals(2, service.getByMusiscGenre(Genre.Pop).size());
        Mockito.verify(artistDAO).findByMusicGenre(Genre.Pop);

        Mockito.when(artistDAO.findByRole(Role.Singer)).thenReturn(artists);
        assertEquals(2, service.getByRole(Role.Singer).size());
        Mockito.verify(artistDAO).findByRole(Role.Singer);

        Mockito.when(artistDAO.findAll()).thenReturn(artists);
        assertEquals(2, service.getAll().size());
        Mockito.verify(artistDAO).findAll();

        Mockito.when(trackDAO.findByArtist("Michel Jackson")).thenReturn(tracks);
        assertEquals(3, service.getTracksByArtist("Michel Jackson").size());
        Mockito.verify(trackDAO).findByArtist("Michel Jackson");
    }
}
