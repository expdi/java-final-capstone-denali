package com.expeditors.musictracking.service;


import com.expeditors.musictracking.dao.ArtistBaseDAO;
import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistBaseDAO artistDAO;

    @Autowired
    private TrackBaseDAO trackDao;

    public List<Artist> getAll() {
        return artistDAO.findAll();
    }

    public Artist getById(int id) {
        return artistDAO.findById(id);
    }

    public Artist insert(Artist artist) {
        return artistDAO.insert(artist);
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
        return artistDAO.update(artist);
    }

    public boolean deleteById(int id) {
        return artistDAO.delete(id);
    }
}
