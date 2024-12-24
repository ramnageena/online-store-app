package com.online_store.service.impl;

import com.online_store.entity.Client;
import com.online_store.reposiitory.ClientRepository;
import com.online_store.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client registerUser(Client client) {
        log.info("Starting to save a new user with username: {}", client.getUsername());
        try {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            Client savedClient = clientRepository.save(client);
            log.info("Successfully saved user with ID: {}", savedClient.getClientId());
            return savedClient;
        } catch (Exception e) {
            log.error("Error occurred while saving user: {}", client.getUsername(), e);
            throw new RuntimeException("Unable to save user. Please try again later.");
        }
    }

    @Override
    public Client getUserById(Long clientId) {
        log.info("Fetching user with ID: {}", clientId);
        try {
            return clientRepository.findById(clientId)
                    .orElseThrow(() -> {
                        log.warn("User not found with ID: {}", clientId);
                        return new UsernameNotFoundException("User not found with ID: " + clientId);
                    });
        } catch (UsernameNotFoundException e) {
            throw e; // Rethrow the specific exception for clarity
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching user with ID: {}", clientId, e);
            throw new RuntimeException("Unable to fetch user details. Please try again later.");
        }
    }

    @Override
    public List<Client> getAllUser() {
        log.info("Fetching all users from the database.");
        try {
            List<Client> clientList = clientRepository.findAll();
            log.info("Successfully fetched {} users from the database.", clientList.size());
            return clientList;
        } catch (Exception e) {
            log.error("Error occurred while fetching all users.", e);
            throw new RuntimeException("Unable to fetch users. Please try again later.");
        }
    }
}
