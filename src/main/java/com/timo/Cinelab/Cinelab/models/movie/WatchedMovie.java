package com.timo.Cinelab.Cinelab.models.movie;

import com.timo.Cinelab.Cinelab.models.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_watched_movie")
@Getter
@Setter
public class WatchedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "review")
    private String review;

    public WatchedMovie(User user, Long movieId, String title, Integer releaseYear, String review) {
        this.user = user;
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.review = review;
    }

    public WatchedMovie() {
    }

}
