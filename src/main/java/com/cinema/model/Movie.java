package com.cinema.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JsonManagedReference
    private List<Client> participants = new ArrayList<>();
}
