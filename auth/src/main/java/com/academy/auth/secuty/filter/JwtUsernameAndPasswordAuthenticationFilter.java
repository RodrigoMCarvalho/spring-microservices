package com.academy.auth.secuty.filter;

import com.academy.core.model.ApplicationUser;
import com.academy.core.property.JwtConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;


public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    static final Logger log = LogManager.getLogger(JwtUsernameAndPasswordAuthenticationFilter.class);

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ApplicationUser applicationUser = null;
        try {
            applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(applicationUser == null)
            throw new UsernameNotFoundException("Unable to retrieve the username or password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                applicationUser.getUsername(), applicationUser.getPassword(), emptyList());

        usernamePasswordAuthenticationToken.setDetails(applicationUser);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        log.info("Authentication was successful for the user '{}', generating JWE token", auth.getName());
    }

    private SignedJWT createSignedJWT(Authentication auth) {
        ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();
        return null;
    }

    private JWTClaimsSet createJWTClaimsSet(Authentication auth, ApplicationUser applicationUser) {
        log.info("Creating the JwtClaimSet Object for '{}'", applicationUser);
        return new JWTClaimsSet.Builder()
                .subject(applicationUser.getUsername())
                .claim("authorities", auth.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(toList()))
                .issuer("http://academy.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfiguration.getExpiration() * 1000)))
                .build();
    }

}