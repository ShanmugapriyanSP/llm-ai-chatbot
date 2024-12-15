package com.personal.ai.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Choice {

    private Integer index;
    private Delta delta;
    private String logProbs;
    @JsonProperty("finish_reason")
    private String finishReason;
    private Message message;

}
