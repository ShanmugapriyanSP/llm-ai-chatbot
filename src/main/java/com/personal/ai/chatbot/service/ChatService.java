package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.clients.ChatApiClient;
import com.personal.ai.chatbot.dto.ChatCompletionRequest;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import com.personal.ai.chatbot.dto.ModelResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatApiClient chatApiClient;

    public Flux<ChatCompletionResponse> streamChatCompletion(ChatCompletionRequest request) {
//        log.info("Calling chat completion API, Prompt: {}", request.getMessages().getLast().getContent());
        return chatApiClient.chatCompletion(request);
    }

    public Mono<ModelResponse> getModels() {
        log.info("Fetching Models...");
        return chatApiClient.getModels();
    }

}
