package org.project.speakeval.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.access-token-expiry-ms}") private long accessExpiry;
    @Value("${jwt.refresh-token-expiry-ms}") private long refreshExpiry;

    private final UserDetailsService userDetailsService;

    private Key key() {
        byte[] bytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(String email, List<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(now())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiry))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiry))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
        String email = claims.getSubject();
        UserDetails user = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Date now() {
        return new Date();
    }
}
