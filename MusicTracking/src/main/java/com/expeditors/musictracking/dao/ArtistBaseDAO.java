package com.expeditors.musictracking.dao;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;

import java.util.List;

public interface ArtistBaseDAO extends BaseDAO<Artist> {
    List<Artist> findByName(String name);

    List<Artist> findByMusicGenre(Genre genre);

    List<Artist> findByRole(Role role);
}
