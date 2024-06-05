package com.expeditors.musictracking.dao.jpa;

import com.expeditors.musictracking.dao.ArtistBaseDAO;
import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Profile("jpa")
public interface JPAArtistDAO extends JpaRepository<Artist, Integer> {
    List<Artist> findByName(String name);

    List<Artist> findByMusicGenre(Genre genre);

    List<Artist> findByRole(Role role);
}
