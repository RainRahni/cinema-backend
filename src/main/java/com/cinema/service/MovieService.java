package com.cinema.service;

import com.cinema.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    List<Movie> getMoviesWithGenre(String movieGenre);
}
