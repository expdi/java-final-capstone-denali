package com.expeditors.musictracking.dao.inmemory;

import com.expeditors.musictracking.dao.BaseDAO;
import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.model.enumerator.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
@Profile("inmemory")
public class TrackDAO implements TrackBaseDAO {

    @Autowired
    private ArtistDAO artistDAO;
    private Map<Integer, Track> tracks = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Track insert(Track newTrack) {
        newTrack.setTrackId(nextId.getAndIncrement());
        tracks.put(newTrack.getTrackId(),newTrack);

        newTrack.getArtists().forEach(artistDAO::insert);

        return newTrack;
    }

    @Override
    public boolean deleteById(int id) {
        return tracks.remove(id) != null;
    }

    @Override
    public boolean update(Track track) {
        return tracks.replace(track.getTrackId(), track) != null;
    }

    @Override
    public Track findById(int id){
        return tracks.get(id);
    }

    @Override
    public List<Track> findAll() {
        return tracks.values().stream().toList();
    }

    @Override
    public List<Track> findByTitle(@NotNull String title) {
        return tracks.values().stream().filter(track -> track.getTitle().contains(title)).toList();
    }

    @Override
    public List<Track> findByArtist(@NotNull String artistName) {
        return tracks.values().stream()
                .filter(track -> track.getArtists() != null)
                .filter(track -> track.getArtists().stream().anyMatch(a -> a.getName().contains(artistName))).toList();
    }

    @Override
    public List<Track> findByAlbum(@NotNull String albumName) {
        return tracks.values().stream().filter(track -> albumName.equals(track.getAlbum())).toList();
    }
    @Override
    public List<Track> findByDuration(@NotNull double duration, Filters filter) {
        List<Track> resultTracks;

        if (filter == null) {
            return new ArrayList<>();
        }

        switch (filter) {
            case Filters.GreaterThan -> resultTracks = tracks.values().stream().filter(track -> track.getDuration() >= duration).toList();
            case Filters.LessThan -> resultTracks = tracks.values().stream().filter(track -> track.getDuration() <= duration).toList();
            case Filters.Equals -> resultTracks = tracks.values().stream().filter(track -> track.getDuration() == duration).toList();
            default -> resultTracks = new ArrayList<>();
        }
        return resultTracks;
    }

    @Override
    public List<Track> findByPrice(@NotNull double price) {
        return tracks.values().stream().filter(track -> track.getLastPrice() >= price).toList();
    }

    @Override
    public List<Track> findMediaType(@NotNull MediaType mediaType) {
        return tracks.values().stream().filter(track -> mediaType.equals(track.getMediaType())).toList();
    }

    @Override
    public List<Track> findByYear(@NotNull int year) {
        Calendar calendar = Calendar.getInstance();
        return tracks.values().stream().filter(track -> {
           calendar.setTime(track.getIssueDate());
           return calendar.get(Calendar.YEAR) == year;
        }).toList();
    }
}
