package com.personal.ai.chatbot.clients;

import com.personal.ai.chatbot.config.ApplicationConfig;
import com.personal.ai.chatbot.dto.ChatCompletionRequest;
import com.personal.ai.chatbot.dto.ChatCompletionResponse;
import com.personal.ai.chatbot.dto.ModelResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatApiClient {

    private final ApplicationConfig config;
    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(config.getChatApiUrl()).build();
    }

    public Flux<ChatCompletionResponse> chatCompletion(ChatCompletionRequest chatCompletionRequest) {
        log.info("Calling LLM with POST method - {}", config.getCompletionEndpoint());
        return webClient.post()
                .uri(config.getCompletionEndpoint())
                .body(Mono.just(chatCompletionRequest), ChatCompletionRequest.class)
                .retrieve()
                .bodyToFlux(ChatCompletionResponse.class)
                .takeWhile(response -> {
                    if (response.getChoices().getLast().getFinishReason() != null) {
                        return !response.getChoices().getLast().getFinishReason().equals("stop");
                    }
                    return true;
                });
    }

    public Mono<ModelResponse> getModels() {
        log.info("Fetching the loaded models");
        return webClient.get()
                .uri(config.getModelEndpoint())
                .retrieve()
                .bodyToMono(ModelResponse.class);
    }
}
