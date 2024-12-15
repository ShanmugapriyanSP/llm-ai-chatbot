package com.personal.ai.chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

//    @Bean
//    public WebClient.Builder webClient() {
//        return WebClient.builder();
//    }

    // Following is a singleton which we are not going to use to provide more flexibility
//    @Bean
//    public WebClient webClient(WebClient.Builder builder) {
//        return builder.build();
//    }
}
