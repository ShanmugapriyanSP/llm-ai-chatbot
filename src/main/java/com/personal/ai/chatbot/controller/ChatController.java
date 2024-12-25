package com.personal.ai.chatbot.controller;

import com.personal.ai.chatbot.dto.*;
import com.personal.ai.chatbot.model.ChatHistory;
import com.personal.ai.chatbot.service.ChatHistoryService;
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
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatHistoryService chatHistoryService;
    private final JWTService jwtService;

    @GetMapping("/models")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ModelResponse> getModels() {
        return chatService.getModels();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ChatCreationResponse> newChat(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return chatService.newChat(getUserId(authHeader));
    }

    @PostMapping(value = "/completion", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<ChatCompletionResponse> chatCompletion(@RequestBody ChatRequest chatRequest,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return chatService.streamChatCompletion(chatRequest, getUserId(authHeader));
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ChatHistory> chatHistory(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return chatHistoryService.getChatHistory(getUserId(authHeader));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck() {
        return "I'm alive";
    }


    private Long getUserId(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtService.extractUserId(token);
    }
}
