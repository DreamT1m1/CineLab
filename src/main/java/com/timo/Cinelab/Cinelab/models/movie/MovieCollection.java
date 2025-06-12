package com.timo.Cinelab.Cinelab.models.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieCollection {

    private Long id;
    private String name;
    private String poster_path;
    private String backdrop_path;

    public MovieCollection() {
    }
}
