package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.config.JwtConfig;
import com.personal.ai.chatbot.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {

    private final String ISSUER = "CHAT AI";

    private final JwtConfig jwtConfig;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("subscription", "premium");
        claims.put("role", user.getRole());
        return generateToken(claims, user);
    }

    public String generateToken(Map<String, Object> claims, User user) {
        return Jwts.builder()
                .claims(claims)
                .issuer(ISSUER)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpiration()))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public Long extractUserId(String jwtToken) {
        Claims claims = extractAllClaims(jwtToken);
        return claims.get("user_id", Long.class);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(jwtToken));
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean isTokenValid(String jwtToken, User user) throws Exception {
        String username = extractUsername(jwtToken);
        log.info(String.valueOf(username.equals(user.getUsername()) && !isTokenExpired(jwtToken)));
        return (username.equals(user.getUsername()) && !isTokenExpired(jwtToken));
    }

    public boolean isTokenExpired(String jwtToken) {
        log.info("Token expiration - {}, Current time - {}", extractExpiration(jwtToken), new Date());
        return extractExpiration(jwtToken).before(new Date());
    }
}
