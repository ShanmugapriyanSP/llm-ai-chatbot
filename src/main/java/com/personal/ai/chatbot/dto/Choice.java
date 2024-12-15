package com.personal.ai.chatbot.dto;

import lombok.Data;

@Data
public class Choice {

    private Integer index;
    private Delta delta;
    private String logProbs;
    private String finishReason;

}
