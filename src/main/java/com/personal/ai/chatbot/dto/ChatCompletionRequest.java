package com.personal.ai.chatbot.dto;



import lombok.Data;

import java.util.List;


@Data
public class ChatCompletionRequest {

    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer maxTokens;
    private Boolean stream;
}
