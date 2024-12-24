package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import com.personal.ai.chatbot.dto.ChatRequest;
import com.personal.ai.chatbot.dto.Message;
import com.personal.ai.chatbot.exceptions.ChatNotFoundException;
import com.personal.ai.chatbot.model.ChatHistory;
import com.personal.ai.chatbot.model.MessageType;
import com.personal.ai.chatbot.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;


    public void saveMessagesToMongoDB(ChatRequest chatRequest, List<ChatCompletionResponse> chatCompletionResponses) {

        Mono<ChatHistory> chatHistoryMono = chatHistoryRepository.findById(new ObjectId(chatRequest.getChatId()));

        ChatHistory.Message assistantMessage = ChatHistory.Message.builder()
                .messageId(UUID.randomUUID().toString())
                .type(MessageType.ASSISTANT)
                .content(getAssistantResponse(chatCompletionResponses))
                .timestamp(Instant.now())
                .build();
        ChatHistory.Message userMessage = ChatHistory.Message.builder()
                .messageId(UUID.randomUUID().toString())
                .type(MessageType.USER)
                .content(getMessageByUserType(chatRequest, MessageType.USER.toString()))
                .timestamp(Instant.now())
                .build();

        chatHistoryMono
                .flatMap(chatHistory -> {
                    chatHistory.setSystemMessage(getMessageByUserType(chatRequest, MessageType.SYSTEM.toString()));
                    chatHistory.getMessages().addAll(List.of(userMessage, assistantMessage));
                    chatHistory.setUpdatedAt(Instant.now());
                    return chatHistoryRepository.save(chatHistory);
                })
                .doOnSuccess(savedChatHistory ->
                        log.info("Saved chat history with Id: {}", savedChatHistory.getId()))
                .doOnError(error -> log.error("Error saving chat history: {}", error.getMessage()))
                .subscribe();  // Subscribe to trigger the execution
}

    private String getAssistantResponse(List<ChatCompletionResponse> chatCompletionResponses) {
        return chatCompletionResponses.stream()
                .map(chatCompletionResponse -> chatCompletionResponse.getChoices().getFirst().getDelta().getContent())
                .collect(Collectors.joining());
    }

    private String getMessageByUserType(ChatRequest chatRequest, String userType) {
        return chatRequest.getChatCompletionRequest()
                .getMessages()
                .stream()
                .filter(message -> message.getRole().equalsIgnoreCase(userType))
                .map(Message::getContent)
                .collect(Collectors.joining());
    }
}