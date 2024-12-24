package com.online_store.security;

import com.online_store.entity.Client;
import com.online_store.reposiitory.ClientRepository;
import com.online_store.reposiitory.ProductRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public CustomUserDetailsService( ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not not with id:" + username));
        return new CustomDetails(client);
    }
}
