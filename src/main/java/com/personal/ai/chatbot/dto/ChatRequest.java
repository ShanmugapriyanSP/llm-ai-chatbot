package com.personal.ai.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    private String chatId;
    private ChatCompletionRequest chatCompletionRequest;
}
