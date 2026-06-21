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

    public WatchedMovie(User user, Long movieId, String title) {
        this.user = user;
        this.movieId = movieId;
        this.title = title;
    }

    public WatchedMovie() {
    }
}
