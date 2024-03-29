package com.cinema.service;

import com.cinema.exception.BadRequestException;
import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private static final String INVALID_PARAMETERS = "Invalid parameters!";
    private static final String NO_SUCH_MOVIE = "No movie with this id!";

    /**
     * Filter movies based on input parameters.
     * @param genre specific movie genre.
     * @param minimumAge specific minimum age required.
     * @param startTime specific start time of a movie.
     * @param language specific language movie is in.
     * @return movies that satisfy filters.
     */
    public List<Movie> filterMovies(String genre,
                                    Integer minimumAge,
                                    LocalTime startTime,
                                    String language) {
        List<Movie> allMovies = movieRepository.findAll();
        return allMovies.stream()
                .filter(movie -> matchesGenre(movie, genre))
                .filter(movie -> matchesMinimumAge(movie, minimumAge))
                .filter(movie -> matchesStartTime(movie, startTime))
                .filter(movie -> matchesLanguage(movie, language))
                .toList();
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

    /**
     * Save given movie to database if correct.
     * @param movie to save to the database.
     */
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

    /**
     * Read all movies currently in the database.
     * @return all movies.
     */
    public List<Movie> readAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Read movie with given id from the database
     * @param id of the movie.
     * @return movie with id.
     */
    public Movie readMovie(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new BadRequestException(NO_SUCH_MOVIE);
        }
        return movie.get();
    }
}
