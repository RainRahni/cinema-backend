package com.cinema.service;

import com.cinema.exception.BadRequestException;
import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService{
    private final MovieRepository movieRepository;
    private final String INVALID_PARAMETERS = "Invalid parameters!";
    private final String NO_SUCH_MOVIE = "No movie with this id!";
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
    }
    public void saveMovie(Movie movie) {
        validateParameters(movie);
        movieRepository.save(movie);
    }
    private void validateParameters(Movie movie) {
        if (movie.getLanguage().isEmpty()
                || movie.getGenre().isEmpty()
                || movie.getDuration() == null
                || movie.getStartTime() == null
                || movie.getName().isEmpty()) {
            throw new BadRequestException(INVALID_PARAMETERS);
        }
    }

    public List<Movie> readAllMovies() {
        return movieRepository.findAll();
    }

    public Movie readMovie(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new BadRequestException(NO_SUCH_MOVIE);
        }
        return movie.get();
    }

    public Integer readRecommendedSeat(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new BadRequestException(NO_SUCH_MOVIE);
        }
        Movie present = movie.get();

        return 1;
    }

    private Integer recommendSeat(List<Boolean> seats) {
        List<List<Boolean>> convertedSeats = convertTo2D(seats);
        return 1;
    }
    private List<List<Boolean>> convertTo2D(List<Boolean> seats) {
        List<List<Boolean>> twoDList = new ArrayList<>();
        twoDList.add(seats.subList(0, 10)); // first row
        for (int i = 0; i < 6; i++) { // next 6 rows
            twoDList.add(seats.subList(10 + i*13, 10 + (i+1)*13));
        }
        return twoDList;
    }
}
