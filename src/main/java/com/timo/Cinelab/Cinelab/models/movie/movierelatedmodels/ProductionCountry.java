package com.timo.Cinelab.Cinelab.models.movie.movierelatedmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionCountry {

    private String iso_3166_1;
    private String name;

    public ProductionCountry() {
    }
}
