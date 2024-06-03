package com.expeditors.musictracking.service;

import com.expeditors.musictracking.dao.BaseDAO;
import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.provider.PriceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackBaseDAO trackDAO;

    @Autowired
    private PriceProvider priceProvider;

    public List<Track> getAll() {
        return setTrackPrice(trackDAO.findAll());
    }

    public Track getById(int id) {
        Track track = trackDAO.findById(id);
        if (track != null) {
            priceProvider.getTrackPrice(track);
        }
        return track;
    }

    public Track insert(Track Track) {
        return trackDAO.insert(Track);
    }

    public List<Track> getByTitle(String title) {
        return setTrackPrice(trackDAO.findByTitle(title));
    }

    public List<Track> getByArtist(String name) {
        return setTrackPrice(trackDAO.findByArtist(name));
    }

    public List<Track> getByMediaType(MediaType mediaType) {
        return setTrackPrice(trackDAO.findMediaType(mediaType));
    }

    public List<Track> getByAlbum(String album) {
        return setTrackPrice(trackDAO.findByAlbum(album));
    }

    public List<Track> getByYear(int year) {
        return setTrackPrice(trackDAO.findByYear(year));
    }

    public List<Track> getByDuration(double duration, Filters filter) {
        return trackDAO.findByDuration(duration,filter);
    }

    public boolean update(Track Track) {
        return trackDAO.update(Track);
    }

    public boolean deleteById(int id) {
        return trackDAO.delete(id);
    }

    public List<Track> setTrackPrice(List<Track> tracks) {
        tracks.forEach(t -> priceProvider.getTrackPrice(t));
        return tracks;
    }


}
