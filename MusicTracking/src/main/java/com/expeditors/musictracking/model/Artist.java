package com.expeditors.musictracking.model;

import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Artist {

    public Artist(){}

    public Artist(String name) {
        this(name,null,null);
    }

    public Artist(String name, Date birthday) {
        this(0,name,0.0,birthday,null,null,null,null);
    }

    public Artist(String name, Genre genre, Role role) {
        this(0,name,0.0,null,null,genre,role,null);
    }

    public Artist(String name, double height, Date birthday, String country, Genre genre, Role role) {
        this(0,name,height,birthday,country,genre,role,null);
    }

    public Artist(int id,String name, double height, Date birthday, String country, Genre genre, Role role) {
        this(0,name,height,birthday,country,genre,role,null);
    }


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artistId;
    @NotNull
    private String name;

    @Nullable
    private Double height;

    private Date birthday;

    private String country;

    @Enumerated(EnumType.STRING)
    private Genre musicGenre;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "rel_track_artist",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    @JdbcTypeCode(SqlTypes.JSON)
    List<Track> tracks;

}

