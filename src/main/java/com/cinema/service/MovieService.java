package com.cinema.service;

import com.cinema.exception.BadRequestException;
import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService{
    private final MovieRepository movieRepository;
    public List<Movie> filterMovies(String genre,
                                    Integer minimumAge,
                                    LocalTime startTime,
                                    String language) {
        List<Movie> allMovies = movieRepository.findAll();
        System.out.println(allMovies.size());
        List<Movie> genres = allMovies.stream().filter(movie -> matchesGenre(movie, genre)).collect(Collectors.toList());
        System.out.println(genres);
        List<Movie> age = genres.stream().filter(movie -> matchesMinimumAge(movie, minimumAge)).collect(Collectors.toList());
        System.out.println(age);
        List<Movie> time = age.stream().filter(movie -> matchesStartTime(movie, startTime)).collect(Collectors.toList());
        System.out.println(time);
        List<Movie> langua = time.stream().filter(movie -> matchesLanguage(movie, language)).collect(Collectors.toList());
        System.out.println(langua);
        System.out.println(language == null);
        return allMovies.stream()
                .filter(movie -> matchesGenre(movie, genre))
                .filter(movie -> matchesMinimumAge(movie, minimumAge))
                .filter(movie -> matchesStartTime(movie, startTime))
                .filter(movie -> matchesLanguage(movie, language))
                .collect(Collectors.toList());
    }
    private boolean matchesGenre(Movie movie, String genre) {
        return genre == null
                || genre.isEmpty()
                || movie.getGenre().isEmpty()
                || movie.getGenre().equalsIgnoreCase(genre);
    }

    private boolean matchesMinimumAge(Movie movie, Integer minimumAge) {
        return minimumAge == null
                || Objects.isNull(movie.getMinimumAge())
                || minimumAge.describeConstable().isEmpty()
                || movie.getMinimumAge() >= minimumAge;
    }

    private boolean matchesStartTime(Movie movie, LocalTime startTime) {
        return startTime == null
                || movie.getStartTime() == null
                || movie.getStartTime().isAfter(startTime);
    }

    private boolean matchesLanguage(Movie movie, String language) {
        return language == null
                || language.isEmpty()
                || (movie.getLanguage().describeConstable().isPresent()
                && movie.getLanguage().equalsIgnoreCase(language));
    }    public void createMovie(Movie movie) {
        validateParameters(movie);
        movieRepository.save(movie);
    }
    private void validateParameters(Movie movie) {
        if (movie.getLanguage().isEmpty()
                || movie.getGenre().isEmpty()
                || movie.getDuration() == null
                || movie.getStartTime() == null
                || movie.getName().isEmpty()) {
            throw new BadRequestException("Invalid parameters");
        }
    }

    public List<Movie> readAllMovies() {
        return movieRepository.findAll();
    }
}
