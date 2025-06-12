package com.timo.Cinelab.Cinelab.models.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Video {

    private static final String YOUTUBE_URL = "https://www.youtube.com/embed/";

    private String iso_639_1;
    private String iso_3166_1;
    private String name;
    private String key;
    private String site;
    private Integer size;
    private String type;
    private Boolean official;
    private String published_at;
    private String id;

    public Video() {
    }

    public String getFullLink() {
        return YOUTUBE_URL + key;
    }
}
