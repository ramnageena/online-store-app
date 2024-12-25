package com.online_store.controller;

import com.online_store.entity.Client;
import com.online_store.entity.JwtResponse;
import com.online_store.jwtHelper.JWTHelper;
import com.online_store.security.CustomUserDetailsService;
import com.online_store.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTHelper jwtHelper;

    public ClientController(ClientService clientService, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JWTHelper jwtHelper) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("/register")
    public ResponseEntity<Client> registerUser(@RequestBody Client client) {
        try {
            Client savedClient = clientService.registerUser(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(client, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Client authModel)  {

        // authenticating the username and password
        doAuthenticate(authModel.getUsername(), authModel.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getUsername());

        //we need to generate the jwt token from here generating token
        final String token = jwtHelper.generateToken(userDetails);

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
}
