package com.online_store.config;

import com.online_store.errortrace.ErrorStack;
import com.online_store.jwtHelper.JWTHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationRequestFilter extends OncePerRequestFilter {

    private final Logger  logger = LoggerFactory.getLogger(JWTAuthenticationRequestFilter.class);

    private final JWTHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    public JWTAuthenticationRequestFilter(JWTHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Authorization
        String requestHeader = request.getHeader("Authorization");

        //Bearer eyJhbGciOiJIUzUxMiJ9
        logger.info(" Header :  {}", requestHeader); //Header :  Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdW etc
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                ErrorStack.printCustomStackTrace(e); // for own simplicity created own printCustomSTackTrace
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                ErrorStack.printCustomStackTrace(e);
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                ErrorStack.printCustomStackTrace(e);
            } catch (Exception e) {
                ErrorStack.printCustomStackTrace(e);
            }
        } else {
            logger.info("Invalid Header Value !! ");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation failed !!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
