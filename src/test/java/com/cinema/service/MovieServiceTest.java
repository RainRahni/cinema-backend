package com.cinema.service;

import com.cinema.exception.BadRequestException;
import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieService movieService;
    private Movie movieOne;
    private Movie movieTwo;
    @BeforeEach
    public void setUp() {
        movieOne = Movie.builder()
                .id(1L)
                .name("one")
                .genre("Action")
                .language("English")
                .minimumAge(10)
                .duration(180)
                .startTime(LocalTime.of(18, 0))
                .build();

        movieTwo = Movie.builder()
                .id(2L)
                .name("two")
                .genre("Romance")
                .language("Estonian")
                .minimumAge(12)
                .startTime(LocalTime.of(12, 30)).build();
    }
    @Test
    void Given_AllParams_When_AllMatch_Then_FilterSuccessful() {
        String genre = "Action";
        String language = "English";
        Integer minimumAge = 10;
        LocalTime startTime = LocalTime.of(12, 0);
        given(movieRepository.findAll()).willReturn(List.of(movieOne, movieTwo));

        var actual = movieService.filterMovies(genre, minimumAge, startTime, language);
        List<Movie> expected = List.of(movieOne);

        then(movieRepository).should().findAll();

        assertEquals(expected, actual);
    }
    @Test
    void Given_OneParam_When_OnlyLang_Then_FilterSuccessful() {
        String language = "Estonian";
        given(movieRepository.findAll()).willReturn(List.of(movieOne, movieTwo));

        var actual = movieService.filterMovies(null, null, null, language);
        List<Movie> expected = List.of(movieTwo);

        then(movieRepository).should().findAll();

        assertEquals(expected, actual);
    }
    @Test
    void Given_OneParam_When_OnlyMinAge_Then_FilterSuccessful() {
        Integer minimumAge = 11;
        given(movieRepository.findAll()).willReturn(List.of(movieOne, movieTwo));

        var actual = movieService.filterMovies(null, minimumAge, null, null);
        List<Movie> expected = List.of(movieTwo);

        then(movieRepository).should().findAll();

        assertEquals(expected, actual);
    }
    @Test
    void Given_OneParam_When_OnlyTime_Then_FilterSuccessful() {
        LocalTime startTime = LocalTime.of(12, 40);
        given(movieRepository.findAll()).willReturn(List.of(movieOne, movieTwo));

        var actual = movieService.filterMovies(null, null, startTime, null);
        List<Movie> expected = List.of(movieOne);

        then(movieRepository).should().findAll();

        assertEquals(expected, actual);
    }
    @Test
    void Given_OneParam_When_OnlyGenre_Then_FilterSuccessful() {
        String genre = "Action";
        given(movieRepository.findAll()).willReturn(List.of(movieOne, movieTwo));

        var actual = movieService.filterMovies(genre, null, null, null);
        List<Movie> expected = List.of(movieOne);

        then(movieRepository).should().findAll();

        assertEquals(expected, actual);
    }
    @Test
    void Given_ValidMovie_When_Saving_Then_SaveSuccessful() {
       movieService.saveMovie(movieOne);

       then(movieRepository).should().save(movieOne);
    }
    @Test
    void Given_InvalidMovie_When_Saving_Then_ThrowError() {
       assertThrows(BadRequestException.class, () -> movieService.saveMovie(movieTwo));
    }
    @Test
    void Given_ValidId_When_Reading_Then_ReadSuccessful() {
        Long id = 1L;
        given(movieRepository.findById(id)).willReturn(Optional.of(movieOne));

        var actual = movieService.readMovie(id);
        Movie expected = movieOne;

        then(movieRepository).should().findById(id);
        assertEquals(expected, actual);
    }
    @Test
    void Given_InvalidId_When_Reading_Then_ThrowError() {
        assertThrows(BadRequestException.class, () -> movieService.readMovie(10L));
    }
}