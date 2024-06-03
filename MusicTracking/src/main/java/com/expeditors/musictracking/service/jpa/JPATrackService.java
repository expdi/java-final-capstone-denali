package com.expeditors.musictracking.service.jpa;

import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.dao.jpa.JPATrackDAO;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.provider.PriceProvider;
import com.expeditors.musictracking.service.TrackBaseService;
import com.expeditors.musictracking.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.expeditors.musictracking.dao.jpa.JPATrackDAO.Specs.byDuration;

@Service
@Transactional
@Profile("jpa")
public class JPATrackService implements TrackBaseService {

    @Autowired
    private JPATrackDAO trackDAO;

    @Autowired
    private PriceProvider priceProvider;

    public List<Track> getAll() {
        return setTrackPrice(trackDAO.findAll());
    }

    public Track getById(int id) {
        Optional<Track> track = trackDAO.findById(id);
        if (track != null) {
            priceProvider.getTrackPrice(track.get());
        }
        return track.get();
    }

    public Track insert(Track Track) {
        return trackDAO.save(Track);
    }

    public List<Track> getByTitle(String title) {
        return setTrackPrice(trackDAO.findByTitle(title));
    }

    public List<Track> getByArtist(String name) {
        return setTrackPrice(trackDAO.findByArtist(name));
    }

    public List<Track> getByMediaType(MediaType mediaType) {
        return setTrackPrice(trackDAO.findByMediaType(mediaType));
    }

    public List<Track> getByAlbum(String album) {
        return setTrackPrice(trackDAO.findByAlbum(album));
    }

    public List<Track> getByYear(int year) {
        return setTrackPrice(trackDAO.findByYear(year));
    }

    public List<Track> getByDuration(double duration, Filters filter) {
        return trackDAO.findAll(byDuration(duration,filter));
    }

    public boolean update(Track track) {
        try {
            if(!trackDAO.existsById(track.getTrackId())) {
                return false;
            }
            trackDAO.save(track);
        }
        catch(Exception ex) {
            return false;
        }
        return true;
    }

    public boolean deleteById(int id) {
        try {
            if(!trackDAO.existsById(id)) {
                return false;
            }
            trackDAO.deleteById(id);
        }
        catch(Exception ex) {
            return false;
        }
        return true;
    }

    public List<Track> setTrackPrice(List<Track> tracks) {
        tracks.forEach(t -> priceProvider.getTrackPrice(t));
        return tracks;
    }


}
