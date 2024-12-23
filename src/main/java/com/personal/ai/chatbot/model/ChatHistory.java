package com.personal.ai.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDateTime;

@Table("chat_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistory {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId; // Link to User.id

    private String model;
    private String systemMessage;
    private String userMessage;
    private String assistantResponse;
    private Double temperature;
    private LocalDateTime timestamp;

}

