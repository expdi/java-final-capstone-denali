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

import java.util.List;

@Service
@Transactional
@Profile("jpa")
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
            artistDAO.save(artist);
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
