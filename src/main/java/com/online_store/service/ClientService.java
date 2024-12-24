package com.online_store.service;

import com.online_store.entity.Client;

import java.util.List;

public interface ClientService {
    Client registerUser(Client client);
    Client getUserById(Long clientId);
    List<Client>getAllUser();
}
