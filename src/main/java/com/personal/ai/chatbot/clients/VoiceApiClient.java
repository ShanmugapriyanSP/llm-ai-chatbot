package com.personal.ai.chatbot.clients;

import com.personal.ai.chatbot.config.ApplicationConfig;
import com.personal.ai.chatbot.dto.VoiceStreamRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoiceApiClient {

    private final ApplicationConfig config;
    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(config.getVoiceApiUrl()).build();
    }

    public Flux<byte[]> generateAudio(VoiceStreamRequest voiceStreamRequest) {
        log.debug("Calling Voice API with POST method - {}", config.getGenerateAudioEndpoint());
        return webClient.post()
                .uri(config.getGenerateAudioEndpoint())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(voiceStreamRequest)
                .accept(MediaType.APPLICATION_OCTET_STREAM) // Ensure you're accepting a binary stream
                .retrieve()
                .bodyToFlux(DataBuffer.class) // Read the response as a stream of DataBuffers
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer); // Release the buffer to avoid memory leaks
                    return bytes;
                });
    }

    public Flux<byte[]> generateAudioStream(VoiceStreamRequest voiceStreamRequest) {
        log.debug("Calling Voice API with POST method - {}", config.getGenerateAudioStreamEndpoint());
        return webClient.post()
                .uri(config.getGenerateAudioStreamEndpoint())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(voiceStreamRequest)
                .accept(MediaType.APPLICATION_OCTET_STREAM) // Ensure you're accepting a binary stream
                .retrieve()
                .bodyToFlux(DataBuffer.class) // Read the response as a stream of DataBuffers
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer); // Release the buffer to avoid memory leaks
                    return bytes;
                });
    }


}
