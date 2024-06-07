package com.expeditors.musictracking.model;

import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.MediaType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Track {

    public Track(String title) {
        this(0,0,title,null,null,null,0.0,null,null,0.0);
    }

    public Track(int identifier, String title, double price) {
        this(0,identifier,title,null,null,null,0.0,null,null,price);
    }

    public Track(String title, String album, LocalDate issueDate) {
        this(0,0,title,album,null,issueDate,0.0,null,null,0.0);
    }

    public Track(int identifier, String title, String album, LocalDate issueDate, Artist artist) {
        this(0,identifier,title,album,List.of(artist),issueDate,0.0,null,null,0.0);
    }

    public Track(int identifier, String title, String album, LocalDate issueDate, Artist artist, double price) {
        this(0,identifier,title,album,List.of(artist),issueDate,0.0,null,null,price);
    }

    public Track(int identifier, String title, String album, LocalDate issueDate, Artist artist, double price, MediaType mediaType) {
        this(0,identifier,title,album,List.of(artist),issueDate,0.0,null,mediaType,price);
    }

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trackId;

    private int trackIdentifier;

    @NotNull
    private String title;

    private String album;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rel_track_artist",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH})
    @JsonIgnoreProperties("tracks")
    List<Artist> artists;

    private LocalDate issueDate;

    @Nullable
    private Double duration;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Nullable
    private Double lastPrice;

}
