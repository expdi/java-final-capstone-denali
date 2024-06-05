package com.expeditors.musictracking.dao.inmemory;

import com.expeditors.musictracking.dao.ArtistBaseDAO;
import com.expeditors.musictracking.dao.BaseDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("inmemory")
public class ArtistDAO implements ArtistBaseDAO {

    private Map<Integer, Artist> artists = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Artist insert(Artist newArtist) {
        //newArtist.getArtistId(nextId.getAndIncrement());
        artists.put(newArtist.getArtistId(),newArtist);
        return newArtist;
    }

    @Override
    public boolean deleteById(int id) {
        return artists.remove(id) != null;
    }

    @Override
    public boolean update(Artist artist) {
        return artists.replace(artist.getArtistId(), artist) != null;
    }

    @Override
    public Artist findById(int id) {
        return artists.get(id);
    }

    @Override
    public List<Artist> findAll() {
        return artists.values().stream().toList();
    }

    @Override
    public List<Artist> findByName(String name) {
        return artists.values().stream().filter(artist -> artist.getName().contains(name)).toList();
    }

    @Override
    public List<Artist> findByMusicGenre(Genre genre) {
        return artists.values().stream().filter(artist -> artist.getMusicGenre().equals(genre)).toList();
    }

    @Override
    public List<Artist> findByRole(Role role) {
        return artists.values().stream().filter(artist -> artist.getRole().equals(role)).toList();
    }
}
