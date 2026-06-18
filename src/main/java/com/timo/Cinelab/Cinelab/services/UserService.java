package com.timo.Cinelab.Cinelab.services;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with email %s was not found", email)));
    }

    public User getUserByUsername(String userName) {
        return userRepository.
                findUserByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s was not found", userName)));
    }
}
