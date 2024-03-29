package com.cinema.service;

import com.cinema.exception.BadRequestException;
import com.cinema.model.Client;
import com.cinema.model.Movie;
import com.cinema.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private MovieService movieService;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientService clientService;
    private Client clientOne;
    private Movie movieOne;
    @BeforeEach
    void setUp() {
        clientOne = Client.builder()
                .id(1L)
                .name("one")
                .email("one@gmail")
                .build();

        movieOne = Movie.builder()
                .id(1L)
                .name("one")
                .genre("Action")
                .language("English")
                .minimumAge(10)
                .duration(180)
                .startTime(LocalTime.of(18, 0))
                .build();
        movieOne.setSeats(new ArrayList<>(Collections.nCopies(88, false)));
        movieOne.setParticipants(new ArrayList<>());
    }
    @Test
    void Given_ValidParam_When_AddingUser_Then_AddingSuccessful() {
        Long movieId = 1L;
        int seatNumber = 10;

        given(movieService.readMovie(movieId)).willReturn(movieOne);

        clientService.addUserToMovie(clientOne, movieId, seatNumber);

        then(movieService).should().readMovie(movieId);
        then(movieService).should().saveMovie(movieOne);
        then(clientRepository).should().save(clientOne);

        List<Boolean> seats = movieService.readMovie(movieId).getSeats();
        var actual = seats.get(seatNumber - 1);
        Boolean expected = true;

        assertEquals(expected, actual);
    }
    @Test
    void Given_InvalidSeat_When_AddingUser_Then_ThrowError() {
        Long movieId = 1L;
        int seatNumber = 0;
        given(movieService.readMovie(movieId)).willReturn(movieOne);

        assertThrows(BadRequestException.class,
                () -> clientService.addUserToMovie(clientOne, movieId, seatNumber));
    }
}
