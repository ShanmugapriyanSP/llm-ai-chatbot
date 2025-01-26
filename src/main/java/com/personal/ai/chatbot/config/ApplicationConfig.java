package com.personal.ai.chatbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.ai.chatbot.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Getter
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Value("${chat.api.url}")
    private String chatApiUrl;

    @Value("${chat.api.completionEndpoint}")
    private String completionEndpoint;

    @Value("${chat.api.modelEndpoint}")
    private String modelEndpoint;

    @Value("${voice.api.url}")
    private String voiceApiUrl;

    @Value("${voice.api.generateAudioEndpoint}")
    private String generateAudioEndpoint;

    @Value("${voice.api.generateAudioStreamEndpoint}")
    private String generateAudioStreamEndpoint;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found in the DB!!"));
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}
