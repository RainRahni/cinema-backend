package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    @Override
    public List<Movie> getMoviesWithGenre(String movieGenre) {
        return movieRepository.findByGenre(movieGenre);
    }
}
