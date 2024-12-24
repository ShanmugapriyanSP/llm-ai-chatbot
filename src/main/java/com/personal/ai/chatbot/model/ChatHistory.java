package com.personal.ai.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Document(collection = "chats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistory {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    @Indexed
    private Long userId;
    private String model;
    private String systemMessage;
    private List<Message> messages;
    private Date createdAt;
    private Date updatedAt;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String messageId;
        private MessageType type;
        private String content;
        private Date timestamp;
    }
}
