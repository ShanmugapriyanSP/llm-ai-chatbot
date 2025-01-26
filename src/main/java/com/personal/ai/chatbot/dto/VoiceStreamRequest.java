package com.personal.ai.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceStreamRequest {
    private String text;
    private String language;
    private Double speed;
    private Boolean stream;
}
