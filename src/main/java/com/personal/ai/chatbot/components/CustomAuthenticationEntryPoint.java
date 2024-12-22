package com.personal.ai.chatbot.components;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.ai.chatbot.dto.UnauthorizedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        UnauthorizedResponse unauthorizedResponse = UnauthorizedResponse.builder()
                .error("Unauthorized")
                .message("You are unauthorized to access this resource")
                .build();

        DataBuffer buffer = null;
        try {
            buffer = exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(unauthorizedResponse));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
