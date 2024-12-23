package com.personal.ai.chatbot.service;

import com.personal.ai.chatbot.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;


//    public void saveChat() {
//        chatHistoryRepository.save();
//    }
}
