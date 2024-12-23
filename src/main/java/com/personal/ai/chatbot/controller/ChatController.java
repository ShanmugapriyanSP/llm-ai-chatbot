package com.personal.ai.chatbot.controller;

import com.personal.ai.chatbot.dto.ChatCompletionRequest;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import com.personal.ai.chatbot.dto.ModelResponse;
import com.personal.ai.chatbot.service.ChatService;
import com.personal.ai.chatbot.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/v1/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final JWTService jwtService;

    @GetMapping("/models")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ModelResponse> getModels() {
        return chatService.getModels();
    }

    @PostMapping(value = "/completion", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<ChatCompletionResponse> chatCompletion(@RequestBody ChatCompletionRequest chatCompletionRequest,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        // Extract userId from the token
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token); // Assuming `userId` is numeric in JWT token
        return chatService.streamChatCompletion(chatCompletionRequest, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck() {
        return "I'm alive";
    }
}
