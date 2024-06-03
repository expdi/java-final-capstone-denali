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

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
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
                    new Date("1950/10/02"),
                    "Texas",
                    Genre.Pop,
                    Role.Singer),
            new Artist(
                    0,
                    "Cristina Aguilera",
                    1.89,
                    new Date("1970/02/02"),
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
    }
}
