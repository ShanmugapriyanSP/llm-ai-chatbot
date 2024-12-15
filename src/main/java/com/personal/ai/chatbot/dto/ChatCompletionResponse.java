package com.personal.ai.chatbot.dto;

import lombok.Data;

import java.util.List;


@Data
public class ChatCompletionResponse {

    private String id;
    private String object;
    private Long created;
    private String model;
    private String systemFingerprint;
    private List<Choice> choices;
}
