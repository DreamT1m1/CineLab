package com.timo.Cinelab.Cinelab.config;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.User.UserRole;
import com.timo.Cinelab.Cinelab.repository.UserRepository;
import com.timo.Cinelab.Cinelab.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // Authorization and general
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/movies/**", "/css/**", "/scripts/**", "/img/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                        .anyRequest().permitAll()
                )
                // Logging in
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/movies", true)
                )
                // Logging out
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                        .build();
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
