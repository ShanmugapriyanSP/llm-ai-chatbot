package com.personal.ai.chatbot.repository;

import com.personal.ai.chatbot.model.ChatHistory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ChatHistoryRepository extends ReactiveMongoRepository<ChatHistory, ObjectId> {
    Mono<ChatHistory> findByUserIdAndId(Long userId, ObjectId id);
    Flux<ChatHistory> findByUserId(Long userId);
}

