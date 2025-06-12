package com.timo.Cinelab.Cinelab.models.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackDrop {

    private Double aspect_ratio;
    private Integer height;
    private String iso_639_1;
    private String file_path;
    private Double vote_average;
    private Integer vote_count;
    private Integer width;

    public BackDrop() {
    }

    @Override
    public String toString() {
        return "BackDrop{" +
                "aspect_ratio=" + aspect_ratio +
                ", height=" + height +
                ", iso_639_1='" + iso_639_1 + '\'' +
                ", file_path='" + file_path + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", width=" + width +
                '}';
    }
}
