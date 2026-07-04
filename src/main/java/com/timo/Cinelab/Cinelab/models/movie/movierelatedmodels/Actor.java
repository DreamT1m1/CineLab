package com.timo.Cinelab.Cinelab.models.movie.movierelatedmodels;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    private Boolean adult;
    private Integer gender;
    private Long id;
    private String known_for_department;
    private String name;
    private String original_name;
    private Double popularity;
    private String profile_path;
    private Integer cast_id;
    private String character;
    private String credit_id;
    private Integer order;

    public String getActorPhotoUrl() {
        return IMAGE_URL + profile_path;
    }
}
