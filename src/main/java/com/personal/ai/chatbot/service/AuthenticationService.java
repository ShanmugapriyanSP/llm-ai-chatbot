package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.dto.AuthenticationResponse;
import com.personal.ai.chatbot.dto.RegisterRequest;
import com.personal.ai.chatbot.exceptions.UserNotFoundException;
import com.personal.ai.chatbot.model.Role;
import com.personal.ai.chatbot.model.User;
import com.personal.ai.chatbot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) throws Exception {
        User user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse authenticate(String username, String password) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password
                )
        );
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException("User is not found!!");

        User user = optionalUser.get();
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
