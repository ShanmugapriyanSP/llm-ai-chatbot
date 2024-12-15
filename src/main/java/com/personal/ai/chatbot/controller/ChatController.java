package com.personal.ai.chatbot.controller;

import com.personal.ai.chatbot.dto.ChatCompletionRequest;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import com.personal.ai.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/v1/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/completion")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<ChatCompletionResponse> chatCompletion(@RequestBody ChatCompletionRequest chatCompletionRequest) {
        return chatService.streamChatCompletion(chatCompletionRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck() {
        return "I'm alive";
    }
}
