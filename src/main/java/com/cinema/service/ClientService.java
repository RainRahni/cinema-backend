package com.cinema.service;

import com.cinema.exception.BadRequestException;
import com.cinema.model.Movie;
import com.cinema.model.Client;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final MovieRepository movieRepository;
    @Transactional
    public void addUserToMovie(Client client, Long movieId, Integer seatNumber) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new BadRequestException("No movie with this id!");
        }
        Movie present = movie.get();
        present.getSeats().add(seatNumber, true);
        present.getParticipants().add(client);
        movieRepository.save(present);
        clientRepository.save(client);
    }
}
