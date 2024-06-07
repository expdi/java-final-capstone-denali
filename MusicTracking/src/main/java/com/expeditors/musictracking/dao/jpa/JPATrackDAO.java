package com.expeditors.musictracking.dao.jpa;

import com.expeditors.musictracking.dao.TrackBaseDAO;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Filters;
import com.expeditors.musictracking.model.enumerator.MediaType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@Profile("jpa")
public interface JPATrackDAO extends JpaRepository<Track,Integer>, JpaSpecificationExecutor<Track> {

    interface Specs {
        static Specification<Track> byDuration(double duration, Filters filter) {
            if (filter == null) {
                return (root, query, builder) ->
                        null;
            }
            return (root, query, builder) ->
                switch (filter) {
                    case Filters.GreaterThan -> builder.greaterThanOrEqualTo(root.get("duration"), duration);
                    case Filters.LessThan -> builder.lessThanOrEqualTo(root.get("duration"), duration);
                    case Filters.Equals -> builder.equal(root.get("duration"), duration);
                    default -> null;
                };

        }
    }
    List<Track> findByTitle(String title);

    @Query("select t from Track t join fetch t.artists a where a.name = :artistName")
    List<Track> findByArtist(String artistName);

    @Query("select t from Track t where year(t.issueDate) = :year")
    List<Track> findByYear(int year);

    List<Track> findByAlbum(String albumName);

    List<Track> findByMediaType(MediaType mediaType);

}
