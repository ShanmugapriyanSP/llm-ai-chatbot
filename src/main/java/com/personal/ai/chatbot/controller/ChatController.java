package com.personal.ai.chatbot.controller;

import com.personal.ai.chatbot.dto.ChatCompletionRequest;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import com.personal.ai.chatbot.dto.ModelResponse;
import com.personal.ai.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/v1/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/models")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Mono<ModelResponse>> getModels() {
        return CompletableFuture.supplyAsync(chatService::getModels);
    }

    @PostMapping(value = "/completion", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Flux<ChatCompletionResponse>> chatCompletion(@RequestBody ChatCompletionRequest chatCompletionRequest) {
        return CompletableFuture.supplyAsync(() -> chatService.streamChatCompletion(chatCompletionRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck() {
        return "I'm alive";
    }
}
