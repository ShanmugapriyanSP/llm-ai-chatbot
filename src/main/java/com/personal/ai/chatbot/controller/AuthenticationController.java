package com.personal.ai.chatbot.controller;

import com.personal.ai.chatbot.dto.AuthenticationResponse;
import com.personal.ai.chatbot.dto.RegisterRequest;
import com.personal.ai.chatbot.exceptions.InvalidCredentialsException;
import com.personal.ai.chatbot.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        return authenticationService.register(registerRequest);
    }

    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<AuthenticationResponse>> authenticate(ServerHttpRequest request) throws Exception {
        final String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            return authenticationService.authenticate(values[0], values[1]);
        }
        throw new InvalidCredentialsException("Authorization header is missing");
    }
}
