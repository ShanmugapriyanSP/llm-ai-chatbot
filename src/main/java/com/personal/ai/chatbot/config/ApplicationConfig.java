package com.personal.ai.chatbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfig {

    @Value("${chat.api.url}")
    private String chatApiUrl;

    @Value("${chat.api.completionEndpoint}")
    private String completionEndpoint;

    @Value("${chat.api.modelEndpoint}")
    private String modelEndpoint;
}
