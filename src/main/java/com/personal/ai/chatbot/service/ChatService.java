package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.clients.ChatApiClient;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import com.personal.ai.chatbot.dto.ChatCreationResponse;
import com.personal.ai.chatbot.dto.ChatRequest;
import com.personal.ai.chatbot.dto.ModelResponse;
import com.personal.ai.chatbot.model.ChatHistory;
import com.personal.ai.chatbot.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatApiClient chatApiClient;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatHistoryService chatHistoryService;

    public Mono<ChatCreationResponse> newChat(Long userId) {
        ChatHistory chatHistory = ChatHistory.builder()
                .userId(userId)
                .model(null)
                .systemMessage(null)
                .messages(Collections.emptyList())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        return chatHistoryRepository.save(chatHistory).map(chat -> new ChatCreationResponse(chat.getId()));
    }

    public Flux<ChatCompletionResponse> streamChatCompletion(ChatRequest chatRequest, Long userId) {
        log.info("UserID: {}, Calling chat completion API, Prompt: {}", userId,
                chatRequest.getChatCompletionRequest().getMessages().getLast().getContent());

        AtomicReference<List<ChatCompletionResponse>> responses = new AtomicReference<>(new ArrayList<>());

        return chatApiClient.chatCompletion(chatRequest.getChatCompletionRequest())
                .doOnNext(chatCompletionResponse -> responses.get().add(chatCompletionResponse))
                .doOnComplete(() -> chatHistoryService.saveMessagesToMongoDB(chatRequest, responses.get()));
    }

    public Mono<ModelResponse> getModels() {
        log.info("Fetching Models...");
        return chatApiClient.getModels();
    }
}
