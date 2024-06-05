package com.expeditors.musictracking.service;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;

import java.util.List;

public interface ArtistBaseService {
    public List<Artist> getAll();

    public Artist getById(int id);

    public Artist insert(Artist artist);

    public List<Artist> getByName(String name);

    public List<Artist> getByRole(Role role);

    public List<Track> getTracksByArtist(String artist);

    public List<Artist> getByMusiscGenre(Genre genre);

    public boolean update(Artist artist) ;

    public boolean deleteById(int id);
}
