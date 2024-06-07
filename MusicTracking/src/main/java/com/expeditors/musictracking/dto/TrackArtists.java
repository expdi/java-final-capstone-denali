package com.expeditors.musictracking.dto;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackArtists {
    public Track track;
    public List<Integer> artistIds;
}
