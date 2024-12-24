package com.personal.ai.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "chats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistory {

    @Id
    private ObjectId id;
    @Indexed
    private Long userId;
    private String model;
    private String systemMessage;
    private List<Message> messages;
    private Instant createdAt;
    private Instant updatedAt;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String messageId;
        private MessageType type;
        private String content;
        private Instant timestamp;
    }
}

