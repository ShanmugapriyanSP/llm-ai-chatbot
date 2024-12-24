package com.personal.ai.chatbot.components;

import com.personal.ai.chatbot.exceptions.InvalidTokenException;
import com.personal.ai.chatbot.service.JWTService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtService.extractUsername(authToken);
        return Mono.just(!jwtService.isTokenExpired(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.error(new InvalidTokenException("Token is expired! Login again...")))
                .map(valid -> {
                    Claims claims = jwtService.extractAllClaims(authToken);
                    String role = claims.get("role", String.class);
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );
                });
    }
}
