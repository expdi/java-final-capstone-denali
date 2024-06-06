package com.expeditors.musictracking.service;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.MediaType;
import org.springframework.context.annotation.Profile;

import java.util.List;

public interface TrackBaseService {
    public List<Track> getAll();

    public Track getById(int id);

    public Track insert(Track Track);

    public List<Track> getByTitle(String title);

    public List<Track> getByArtist(String name);

    public List<Track> getByMediaType(MediaType mediaType);

    public List<Track> getByAlbum(String album);

    public List<Track> getByYear(int year);

    public List<Track> getByDuration(double duration, Filters filter);

    public Track addTrackArtists(Track track ,List<Integer> artistIds);

    public Track addTracksNewArtists(Track track ,List<Artist> artists);

    public boolean update(Track Track);

    public boolean deleteById(int id);

    public List<Track> setTrackPrice(List<Track> tracks) ;
}
