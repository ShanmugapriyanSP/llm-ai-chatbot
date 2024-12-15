package com.personal.ai.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    private String id;
    private String object;
    private String type;
    private String publisher;
    private String arch;
    private String compatibilityType;
    private String quantization;
    private String state;
    private String maxContentLength;
}
