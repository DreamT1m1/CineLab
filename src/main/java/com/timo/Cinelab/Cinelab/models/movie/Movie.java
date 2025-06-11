package com.timo.Cinelab.Cinelab.models.movie;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie {

    private Boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private Long id;
    private String original_language;
    private String original_title;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private Boolean video;
    private Integer vote_average;
    private Integer vote_count;

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "original_title='" + original_title + '\'' +
                ", original_language='" + original_language + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }

    public String getMoviePosterUrl() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }
}
