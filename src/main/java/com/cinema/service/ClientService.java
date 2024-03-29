package com.cinema.service;

import com.cinema.exception.BadRequestException;
import com.cinema.model.Client;
import com.cinema.model.Movie;
import com.cinema.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final MovieService movieService;
    private static final String NO_SUCH_SEAT = "Invalid seat number!";

    /**
     * Add user to database and edit the seats for given movie.
     * @param client that registered to movie.
     * @param movieId movie client registered to.
     * @param seatNumber where client sits.
     */
    @Transactional
    public void addUserToMovie(Client client, Long movieId, Integer seatNumber) {
        Movie movie = movieService.readMovie(movieId);
        if (seatNumber - 1 < 0) {
            throw new BadRequestException(NO_SUCH_SEAT);
        }
        movie.getSeats().add(seatNumber - 1, true);
        movie.getParticipants().add(client);
        client.setMovie(movie);
        movieService.saveMovie(movie);
        clientRepository.save(client);
    }
}
