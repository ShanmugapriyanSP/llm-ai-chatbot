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
    private final ChatHistoryService chatHistoryService;

    public Flux<ChatCompletionResponse> streamChatCompletion(ChatCompletionRequest request, Long userId) {
        log.info("Calling chat completion API, Prompt: {}", request.getMessages().getLast().getContent());
        return chatApiClient.chatCompletion(request);
    }
//                .doOnNext(response -> {
//                    // Save the chat message in the DB as each response arrives
//                    saveChatHistoryInParallel(userId, response);
//                });
//    }
//
//    private void saveChatHistoryInParallel(Long userId, ChatCompletionResponse response) {
//        // Prepare chat history details (You can customize based on your response structure)
//        String model = response.getModel();
//        String systemMessage = extractMessageByRole(response, "system");
//        String userMessage = extractMessageByRole(response, "user");
//        String assistantResponse = response.getChoices().getLast().getText();
//
//        // Save the chat history in parallel without blocking the stream
//        chatHistoryService.saveChat(userId, model, systemMessage, userMessage, assistantResponse)
//                .subscribe();  // This will run asynchronously
//    }
//
//    private String extractMessages(ChatCompletionResponse response) {
//        // Extract the message from the response based on the role (you may need to adapt this logic)
//        return response.getChoices().getFirst().getDelta().getContent();
//    }

    public Mono<ModelResponse> getModels() {
        log.info("Fetching Models...");
        return chatApiClient.getModels();
    }
}
