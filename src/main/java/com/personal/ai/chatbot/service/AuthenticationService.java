package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.components.AuthenticationManager;
import com.personal.ai.chatbot.dto.AuthenticationResponse;
import com.personal.ai.chatbot.dto.RegisterRequest;
import com.personal.ai.chatbot.exceptions.EmailAlreadyExistsException;
import com.personal.ai.chatbot.model.Role;
import com.personal.ai.chatbot.model.User;
import com.personal.ai.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Mono<AuthenticationResponse> register(RegisterRequest registerRequest) {
        return userRepository.findByEmail(registerRequest.getEmail())
                .hasElement()
                .flatMap(emailExists -> {
                    if (Boolean.TRUE.equals(emailExists)) {
                        return Mono.error(new EmailAlreadyExistsException("Email is already taken"));
                    }
                    User user = User.builder()
                            .firstname(registerRequest.getFirstname())
                            .lastname(registerRequest.getLastname())
                            .email(registerRequest.getEmail())
                            .password(passwordEncoder.encode(registerRequest.getPassword()))
                            .role(Role.USER.toString())
                            .build();
                    return userRepository.save(user)
                            .map(savedUser -> new AuthenticationResponse(jwtService.generateToken(savedUser)));
                });
    }

    public Mono<ResponseEntity<AuthenticationResponse>> authenticate(String username, String password) {
        return userRepository.findByEmail(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> ResponseEntity.ok(new AuthenticationResponse(jwtService.generateToken(user))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}
