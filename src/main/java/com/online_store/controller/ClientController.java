package com.online_store.controller;

import com.online_store.entity.Client;
import com.online_store.exception.UserNotFoundException;
import com.online_store.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;

    }

    @PostMapping("/clients/register")
    public ResponseEntity<Client> registerUser(@RequestBody Client client) {
        try {
            Client savedClient = clientService.registerUser(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getClient/{id}")
    public ResponseEntity<Client> getUserById(@PathVariable Long id) {
        try {
            Client client = clientService.getUserById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getALlClients")
    public ResponseEntity<List<Client>> getAllUsers() {
        try {
            List<Client> clients = clientService.getAllUser();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
