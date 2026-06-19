package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmailIgnoreCase(String email);
    Optional<User> findUserByUsername(String username);

}
