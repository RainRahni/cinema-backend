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
    private static final String NO_SUCH_MOVIE = "No movie with this id!";
    private static final String NO_SUCH_SEAT = "Invalid seat number!";
    @Transactional
    public void addUserToMovie(Client client, Long movieId, Integer seatNumber) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new BadRequestException(NO_SUCH_MOVIE);
        }
        Movie present = movie.get();
        if (seatNumber - 1 < 0) {
            throw new BadRequestException(NO_SUCH_SEAT);
        }
        present.getSeats().add(seatNumber - 1, true);
        present.getParticipants().add(client);
        client.setMovie(present);
        movieRepository.save(present);
        clientRepository.save(client);
    }
}
