package com.clay.sh.identityserver.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

public interface JwtTokenProvider {
    String generateToken(Authentication authentication);

    boolean validateToken(String token, UserDetails userDetails);

    String extractUsername(String token);

    Date extractExpiration(String token);
}
