package com.personal.ai.chatbot.clients;

import com.personal.ai.chatbot.config.ApplicationConfig;
import com.personal.ai.chatbot.dto.ChatCompletionRequest;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChatApiClient {

    private final ApplicationConfig config;
    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(config.getChatApiUrl()).build();
    }

    public Flux<ChatCompletionResponse> chatCompletion(ChatCompletionRequest chatCompletionRequest) {
        return webClient.post()
                .uri(config.getCompletionEndpoint())
                .body(Mono.just(chatCompletionRequest), ChatCompletionRequest.class)
                .retrieve()
                .bodyToFlux(ChatCompletionResponse.class);
    }
}
