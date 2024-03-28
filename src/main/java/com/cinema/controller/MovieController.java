package com.cinema.controller;

import com.cinema.model.Movie;
import com.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;
    @GetMapping("/filter")
    public List<Movie> filterMovies(@RequestParam(required = false) String genre,
                                    @RequestParam(required = false) Integer minimumAge,
                                    @RequestParam(required = false) LocalTime startTime,
                                    @RequestParam(required = false) String language) {
        return movieService.filterMovies(genre, minimumAge, startTime, language);
    }
    @PostMapping
    public void createMovie(@RequestBody Movie movie) {
        movieService.createMovie(movie);
    }
    @GetMapping("/all")
    public List<Movie> readAllMovies() {
        return movieService.readAllMovies();
    }
}
