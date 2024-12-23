package com.personal.ai.chatbot.repository;

import com.personal.ai.chatbot.model.ChatHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatHistoryRepository extends ReactiveCrudRepository<ChatHistory, Long> {
    Flux<ChatHistory> findByUserId(Long userId);
}

