package com.timo.Cinelab.Cinelab.models.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 30)
    private int id;
    @Column(name = "login", nullable = false, length = 30)
    private String username;
    @Column(name = "email", nullable = false, length = 30)
    private String email;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "password", nullable = false, length = 60)
    private String password;
    @Column(name = "role", nullable = false)
    private UserRole role;

    public User() {}

    public User(String username, String email, String name, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
