package com.expeditors.musictracking;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.model.enumerator.Role;
import com.expeditors.musictracking.service.ArtistBaseService;
import com.expeditors.musictracking.service.TrackBaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static java.lang.System.out;

@EnableJpaRepositories
@SpringBootApplication
public class MusicTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicTrackingApplication.class, args);
	}

}

//@Component
//class dbInit implements CommandLineRunner {
//	@Autowired
//	private ArtistBaseService artistService;
//
//	@Autowired
//	private TrackBaseService trackService;
//
//	@Override
//	@Transactional
//	public void run(String... args) {
//		var artists = List.of(
//				new Artist(0, "Coldplay", 0, null, "United Kingdom", Genre.Pop, null),
//				new Artist(0, "Red Hot Chili Peppers", 0, null, "United States", Genre.Rock, null),
//				new Artist(0, "Rihanna", 1.60, new Date(1983, 10, 2), "Barbados", Genre.Pop, Role.Singer),
//				new Artist(0, "Michael Jackson", 1.80, null, "United States", Genre.Pop, Role.Singer),
//				new Artist(0, "Chad Smith", 1.80, new Date(1961, 10, 25), "United States", Genre.Rock, Role.Drummer)
//		);
//		artists.forEach(artistService::insert);
//
//		List<Artist> resultArtists = artistService.getAll();
//		out.println("Artists count: " + resultArtists.size());
//
//		var artists1 = List.of(
//				new Artist(1, "Coldplay", 0, null, "United Kingdom", Genre.Pop, null)
//		);
//
//		var artists2 = List.of(
//				new Artist(3, "Rihanna", 1.60, new Date(1983, 10, 2), "Barbados", Genre.Pop, Role.Singer),
//				new Artist(4, "Michael Jackson", 1.80, null, "United States", Genre.Pop, Role.Singer)
//		);
//
//		var tracks = List.of(
//				new Track(0, 1, "Viva La Vida", "Viva la Vida or Death and All His Friends",
//						artists1, new Date(2008, 04, 20), 2.40, Genre.Pop, MediaType.MP3, null),
//				new Track(0, 2, "Strawberry Swing", "Viva la Vida or Death and All His Friends",
//						artists2, new Date(2008, 04, 10), 1.56, Genre.Pop, MediaType.MP3, null),
//				new Track(0, 3, "21st Century", "Stadium Arcadium",
//						null, new Date(2008, 04, 10), 1.45, Genre.Rock, MediaType.Ogg, null),
//				new Track(0, 4, "All of the Lights", "My Beautiful Dark Twisted Fantasy",
//						null, new Date(2011, 05, 16), 2.25, Genre.Pop, MediaType.Ogg, null),
//				new Track(0, 5, "Beat It", "Thriller",
//						null, new Date(1195, 11, 26), 2.65, Genre.Pop, MediaType.WAV, null)
//		);
//		tracks.forEach(trackService::insert);
//
////		List<Track> resultTracks = trackService.getAll();
////		out.println("Tracks count: " + resultTracks.size());
//
//
//	}
//}
