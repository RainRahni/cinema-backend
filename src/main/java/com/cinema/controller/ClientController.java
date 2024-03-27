package com.cinema.controller;

import com.cinema.model.Client;
import com.cinema.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/")
    public void addUserToMovie(@RequestBody Client client,
                               @RequestParam Long movieId,
                               @RequestParam Integer seatNumber) {
        clientService.addUserToMovie(client, movieId, seatNumber);
    }
}
