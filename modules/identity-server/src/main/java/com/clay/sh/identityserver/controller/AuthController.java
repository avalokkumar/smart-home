package com.clay.sh.identityserver.controller;

import com.clay.sh.identityserver.model.ClientCredentialsRequest;
import com.clay.sh.identityserver.model.ClientDetails;
import com.clay.sh.identityserver.model.JwtAuthenticationResponse;
import com.clay.sh.identityserver.model.LoginRequest;
import com.clay.sh.identityserver.service.ClientDetailsService;
import com.clay.sh.identityserver.service.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ClientDetailsService clientDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                          ClientDetailsService clientDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.clientDetailsService = clientDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody ClientCredentialsRequest request) {
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(request.getClientId());
        if (!clientDetails.getClientSecret().equals(request.getClientSecret())) {
            throw new BadCredentialsException("Invalid client credentials");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(clientDetails.getClientId(), clientDetails.getClientSecret()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
