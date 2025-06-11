package com.timo.Cinelab.Cinelab.movieapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timo.Cinelab.Cinelab.models.Movie;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MovieApi {

    private static final String MOVIES_PAGE_URL = "https://api.themoviedb.org/3/discover/movie?page=";
    private static final String MOVIES_BY_TITLE_URL = "https://api.themoviedb.org/3/search/movie?api_key=YOUR_API_KEY&query=";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public MovieApi() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept", "application/json");
        httpHeaders.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMTI1ODMwZjZhMjI3NGFhNWQ0MDVkZDYyZWE3NGU2OCIsIm5iZiI6MTc0OTYzOTEwNy4zMTcsInN1YiI6IjY4NDk1ZmMzNDAwOWNiOGYwOTNlMDkyZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KSPcjT4au9R3HfMiR1E-Pr6xf69t7PVM5e7okoDJ3EY");
        return httpHeaders;
    }

    public List<Movie> getMoviePage(int page) {
        try {
            String finalUrl = MOVIES_PAGE_URL + page;

            HttpEntity<String> httpEntity = new HttpEntity<>(getHttpHeaders());

            String response = restTemplate.exchange(
                    finalUrl,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            ).getBody();

            return objectMapper.convertValue(
                    objectMapper.readTree(response).get("results"),
                    new TypeReference<>() {}
            );
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public List<Movie> getMoviesByTitle(String title) {
        try {
            String finalUrl = MOVIES_BY_TITLE_URL + title.replaceAll(" ", "+");

            HttpEntity<String> httpEntity = new HttpEntity<>(getHttpHeaders());

            String response = restTemplate.exchange(
                    finalUrl,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            ).getBody();

            return objectMapper.convertValue(
                    objectMapper.readTree(response).get("results"),
                    new TypeReference<>() {}
            );
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public static void main(String[] args){
        MovieApi movieApi = new MovieApi();
        movieApi.getMoviesByTitle("Batman").forEach(m -> System.out.println(m + " " + m.getOverview()));
    }
}
