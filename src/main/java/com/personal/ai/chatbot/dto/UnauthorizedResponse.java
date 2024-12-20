package com.personal.ai.chatbot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnauthorizedResponse {

    private String error;
    private String message;
}
