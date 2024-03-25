package com.cinema.controller;

import com.cinema.model.Movie;
import com.cinema.service.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieServiceImpl movieService;
    @GetMapping("/{type}")
    public List<Movie> getMoviesWithGenre(@PathVariable("type") String movieGenre) {
        return movieService.getMoviesWithGenre(movieGenre);
    }
}
