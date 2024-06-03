package com.expeditors.musictracking;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.expeditors.musictracking.model.enumerator.Role;
import com.expeditors.musictracking.service.ArtistService;
import com.expeditors.musictracking.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class MusicTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicTrackingApplication.class, args);
	}

}

@Component
class ContextApp implements CommandLineRunner {

	@Autowired
	private TrackService trackService;

	@Autowired
	private ArtistService artistService;

	@Override
	public void run(String... args) throws Exception {

		List<Artist> artists =  List.of(new Artist(
				0,
				"Gativideo",
				0,
				new Date("2010/02/23"),
				"Spain",
				Genre.Pop,
				Role.Producer),new Artist(
						1,
						"Michel Jackson",
						1.60,
						new Date("1950/10/02"),
						"Texas",
						Genre.Pop,
						Role.Singer),
				new Artist(
						2,
						"Cristina Aguilera",
						1.89,
						new Date("1970/02/02"),
						"Arizona",
						Genre.Pop,
						Role.Singer));

		List<Track> tracks = List.of(
				new Track(
						1,
						233,
						"Love",
						"My Love is the end",
						List.of( new Artist(
										1,
										"Michel Jackson",
										1.60,
										new Date("1950/10/02"),
										"Texas",
										Genre.Pop,
										Role.Singer),
								new Artist(
										2,
										"Cristina Aguilera",
										1.89,
										new Date("1970/02/02"),
										"Arizona",
										Genre.Pop,
										Role.Singer)),
						new Date("2000/10/02"),
						3.45,
						Genre.Pop,
						MediaType.MP3,
						10.40),
				new Track(
						0,
						111,
						"Memories",
						"World is fine",
						List.of( new Artist(
										1,
										"Michel Jackson",
										1.60,
										new Date("1950/10/02"),
										"Texas",
										Genre.Pop,
										Role.Singer),
								new Artist(
										2,
										"Cristina Aguilera",
										1.89,
										new Date("1970/02/02"),
										"Arizona",
										Genre.Pop,
										Role.Singer)),
						new Date("1981/10/02"),
						1.20,
						Genre.Pop,
						MediaType.Ogg,
						10.40),
				new Track(
						213,
						"Disco Majul",
						"Colorama",
						new Date("2029/10/02"),
						new Artist(
								20,
								"Gativideo",
								0,
								new Date("2010/02/23"),
								"Spain",
								Genre.Pop,
								Role.Producer),
						10.40));

		tracks.forEach(trackService::insert);
		artists.forEach(artistService::insert);
	}


}
