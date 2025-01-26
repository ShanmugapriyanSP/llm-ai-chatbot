package com.personal.ai.chatbot.controller;

import com.personal.ai.chatbot.dto.GenerateAudioRequest;
import com.personal.ai.chatbot.service.JWTService;
import com.personal.ai.chatbot.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/voice")
@RequiredArgsConstructor
public class VoiceController {

    private final VoiceService voiceService;
    private final JWTService jwtService;


    @PostMapping(value = "/generate", produces = "audio/wav")
    @ResponseStatus(HttpStatus.OK)
    public Flux<byte[]> generateAudio(@RequestBody GenerateAudioRequest generateAudioRequest,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return voiceService.generateAudio(generateAudioRequest, getUserId(authHeader));
    }



//    @PostMapping(value = "/completion", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Flux<byte[]> chatCompletion(@RequestBody ChatRequest chatRequest,
//                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
//        return voiceService.generateAudio(chatRequest, getUserId(authHeader));
//    }

    private Long getUserId(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtService.extractUserId(token);
    }
}
