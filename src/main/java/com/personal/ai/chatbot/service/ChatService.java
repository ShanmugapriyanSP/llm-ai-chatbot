package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.clients.ChatApiClient;
import com.personal.ai.chatbot.dto.ChatCompletionRequest;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatApiClient chatApiClient;

    public Flux<ChatCompletionResponse> streamChatCompletion(ChatCompletionRequest request) {
        return chatApiClient.chatCompletion(request);
    }

}
