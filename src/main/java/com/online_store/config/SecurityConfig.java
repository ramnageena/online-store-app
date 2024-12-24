package com.online_store.config;

import com.online_store.constant.SecurityConstant;
import com.online_store.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.online_store.constant.SecurityConstant.*;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for REST APIs
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(REGISTER_CLIENT,LOGIN_CLIENT).permitAll() // Public: Register client
                    .requestMatchers(UPDATE_PRODUCT, DELETE_PRODUCT, GET_CLIENT_BY_ID, GET_ALL_CLIENTS).hasAuthority(ROLE_ADMIN) // Only Admin can perform these actions
                    .requestMatchers(NEW_PRODUCT, GET_ALL_PRODUCTS, GET_PRODUCT_BY_ID).hasAnyAuthority(ROLE_USER, ROLE_ADMIN) // Both Admin and User can access
                    .anyRequest().authenticated() // Other requests require authentication
            )
            .httpBasic(Customizer.withDefaults()); // Basic Authentication

    return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
