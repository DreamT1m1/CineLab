package com.timo.Cinelab.Cinelab.models.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionCompany {
    private Integer id;
    private String name;
    private String logo_path;
    private String origin_country;

    public ProductionCompany() {
    }
}
