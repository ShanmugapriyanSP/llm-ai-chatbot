package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.clients.VoiceApiClient;
import com.personal.ai.chatbot.dto.GenerateAudioRequest;
import com.personal.ai.chatbot.dto.VoiceStreamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class VoiceService {

    private final VoiceApiClient  voiceApiClient;
    private final ChatService chatService;

    public Flux<byte[]> generateAudio(GenerateAudioRequest generateAudioRequest, Long userId) {
        VoiceStreamRequest voiceStreamRequest = VoiceStreamRequest.builder()
                .text(generateAudioRequest.getText())
                .speed(generateAudioRequest.getSpeed())
                .language(generateAudioRequest.getLanguage())
                .stream(generateAudioRequest.getStream())
                .build();
        return voiceApiClient.generateAudio(voiceStreamRequest);
    }

//    public Flux<byte[]> generateAudio(ChatRequest chatRequest, Long userId) {
//        return chatService.streamChatCompletion(chatRequest, userId)
//                .doOnNext( chatCompletionResponse -> {
//                    VoiceStreamRequest voiceStreamRequest = VoiceStreamRequest.builder()
//                            .text(chatCompletionResponse.getChoices().getFirst().getDelta().getContent())
//                            .language("en")
//                            .speed(0.8)
//                            .stream(true)
//                            .build();
//                    voiceApiClient.generateAudioStream(voiceStreamRequest)
//                })
//    }
}
