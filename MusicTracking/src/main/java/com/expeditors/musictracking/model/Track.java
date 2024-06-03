package com.expeditors.musictracking.model;

import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    public Track(String title) {
        this(0,0,title,null,null,null,0,null,null,0);
    }

    public Track(int identifier, String title, double price) {
        this(0,identifier,title,null,null,null,0,null,null,price);
    }

    public Track(String title, String album, Date issueDate) {
        this(0,0,title,album,null,issueDate,0,null,null,0);
    }

    public Track(int identifier, String title, String album, Date issueDate, Artist artist) {
        this(0,identifier,title,album,List.of(artist),issueDate,0,null,null,0);
    }

    public Track(int identifier, String title, String album, Date issueDate, Artist artist, double price) {
        this(0,identifier,title,album,List.of(artist),issueDate,0,null,null,price);
    }

    @NotNull
    private int id;

    private int trackIdentifier;

    @NotNull
    private String title;

    private String album;

    private List<Artist> artists;

    private Date issueDate;

    private double duration;

    private Genre genre;

    private MediaType mediaType;

    private double price;
}
