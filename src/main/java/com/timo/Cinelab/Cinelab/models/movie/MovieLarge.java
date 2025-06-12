package com.timo.Cinelab.Cinelab.models.movie;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieLarge {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    private Boolean adult;
    private String backdrop_path;
    private MovieCollection belongs_to_collection;
    private Integer budget;
    private List<Genre> genres;
    private String homepage;
    private Long id;
    private String imdb_id;
    private List<String> origin_country;
    private String original_language;
    private String original_title;
    private String overview;
    private Double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private String release_date;
    private Long revenue;
    private Integer runtime;
    private List<SpokenLanguage> spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private Boolean video;
    private Double vote_average;
    private Integer vote_count;

    public MovieLarge() {
    }

    @Override
    public String toString() {
        return "MovieLarge{" +
                "original_title='" + original_title + '\'' +
                '}';
    }

    public String getRuntimeInHoursAndMinutes() {
        return String.format("%dh %dm", getRuntime() / 60, getRuntime() % 60);
    }

    public String getMoviePosterUrl() {
        return IMAGE_URL + poster_path;
    }
}
