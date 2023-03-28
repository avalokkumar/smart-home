package com.clay.sh.identityserver.service;

import com.clay.sh.identityserver.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

    @Value("${jwt.secret:s3cret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms:100000}")
    private Long jwtExpirationMs;

    @Override
    public String generateToken(Authentication authentication) {
        // Get user details from authentication object
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Create a map to store any custom claims
        Map<String, Object> claims = new HashMap<>();

        // Build JWT token with subject as the username, issued at current time, and expiration time
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                // Sign the token with the HS512 algorithm and secret key
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        // Extract username from token
        final String username = extractUsername(token);

        // Check if username matches user details and token is not expired
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String extractUsername(String token) {
        // Extract username from token's subject field
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        // Extract expiration date from token's expiration field
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Extract claim of generic type T using a provided Claims resolver function
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // Parse all claims from token using secret key
        return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        // Check if current date is after token's expiration date
        return extractExpiration(token).before(new Date());
    }
}

