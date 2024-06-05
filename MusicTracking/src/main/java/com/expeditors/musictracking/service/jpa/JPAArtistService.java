package com.expeditors.musictracking.service.jpa;


import com.expeditors.musictracking.dao.ArtistBaseDAO;
import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.dao.jpa.JPAArtistDAO;
import com.expeditors.musictracking.dao.jpa.JPATrackDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import com.expeditors.musictracking.service.ArtistBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Service
@Transactional
public class JPAArtistService implements ArtistBaseService {

    @Autowired
    private JPAArtistDAO artistDAO;

    @Autowired
    private JPATrackDAO trackDao;

    public List<Artist> getAll() {
        return artistDAO.findAll();
    }

    public Artist getById(int id) {
        if (artistDAO.existsById(id)) {
            return artistDAO.findById(id).get();
        }
        return null;
    }

    public Artist insert(Artist artist) {
        return artistDAO.save(artist);
    }

    public List<Artist> getByName(String name) {
        return artistDAO.findByName(name);
    }

    public List<Artist> getByRole(Role role) {
        return artistDAO.findByRole(role);
    }

    public List<Track> getTracksByArtist(String artist) {
        return trackDao.findByArtist(artist);
    }

    public List<Artist> getByMusiscGenre(Genre genre) {
        return artistDAO.findByMusicGenre(genre);
    }

    public boolean update(Artist artist) {
        try {
            if(!artistDAO.existsById(artist.getArtistId())) {
                return false;
            }

            Artist updatedArtist = artistDAO.findById(artist.getArtistId()).orElse(null);
            List<Track> tracks = new ArrayList<>();
            artist.getTracks().forEach(track -> {
                Track findedTrack = trackDao.findById(track.getTrackId()).orElse(null);
                if(findedTrack == null) {
                    tracks.add(track);
                } else {
                    Optional<Track> trackItem = updatedArtist.getTracks().stream().filter(track1 -> track1.getTrackId() == track.getTrackId()).findFirst();
                    if (trackItem.isPresent()) {
                        trackItem.get().setAlbum(track.getAlbum());
                        trackItem.get().setGenre(track.getGenre());
                        trackItem.get().setTitle(track.getTitle());
                        trackItem.get().setDuration(track.getDuration());
                        trackItem.get().setIssueDate(track.getIssueDate());
                        trackItem.get().setMediaType(track.getMediaType());
                        trackItem.get().setTrackIdentifier(track.getTrackIdentifier());
                    } else {
                        tracks.add(findedTrack);
                    }
                }
            });

            updatedArtist.setName(artist.getName());
            updatedArtist.setHeight(artist.getHeight());
            updatedArtist.setCountry(artist.getCountry());
            updatedArtist.setBirthday(artist.getBirthday());
            updatedArtist.setMusicGenre(artist.getMusicGenre());
            updatedArtist.setRole(artist.getRole());

            updatedArtist.getTracks().addAll(tracks);

            artistDAO.save(updatedArtist);
        }
        catch(Exception ex) {
            return false;
        }
        return true;
    }

    public boolean deleteById(int id) {
        try {
            if(!artistDAO.existsById(id)) {
                return false;
            }
            artistDAO.deleteById(id);
        }
        catch(Exception ex) {
            return false;
        }
        return true;
    }
}
