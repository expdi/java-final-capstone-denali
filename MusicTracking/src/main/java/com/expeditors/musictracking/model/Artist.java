package com.expeditors.musictracking.model;

import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    public Artist(String name) {
        this(name,null,null);
    }

    public Artist(String name, Date birthday) {
        this(0,name,0,birthday,null,null,null);
    }

    public Artist(String name, Genre genre, Role role) {
        this(0,name,0,null,null,genre,role);
    }

    public Artist(String name, double height, Date birthday, String country, Genre genre, Role role) {
        this(0,name,height,birthday,country,genre,role);
    }


    @NotNull
    private int id;

    @NotNull
    private String name;

    private double height;

    private Date birthday;

    private String country;

    private Genre musicGenre;

    private Role role;
}
