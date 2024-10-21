package com.example.gaffer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gaffer.models.ChatMessage;
import com.example.gaffer.models.MessageStatus;
import com.example.gaffer.repositories.ChatMessageRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {
    private ChatMessageRepository repository;
    private ChatRoomService chatRoomService;

    @Autowired
    public ChatMessageService(ChatMessageRepository repository, ChatRoomService chatRoomService) {
        this.repository = repository;
        this.chatRoomService = chatRoomService;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages =
                chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public ChatMessage findById(String id) throws Exception {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new Exception("can't find message (" + id + ")"));
    }
    @Transactional
    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        repository.updateStatusBySenderIdAndRecipientId(senderId, recipientId, status);
    }
}