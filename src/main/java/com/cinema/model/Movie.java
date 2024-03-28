package com.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer duration;
    private String genre;
    private String language;
    private Integer minimumAge;
    private LocalTime startTime;
    @ElementCollection
    private List<Boolean> seats = Collections.nCopies(88, false);
    @OneToMany(mappedBy = "movie")
    private List<Client> participants = new ArrayList<>();
}
