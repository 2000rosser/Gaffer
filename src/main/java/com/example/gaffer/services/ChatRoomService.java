package com.example.gaffer.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gaffer.models.ChatRoom;
import com.example.gaffer.repositories.ChatRoomRepository;

@Service
public class ChatRoomService {

    @Autowired private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(
            String senderId, String recipientId, boolean createIfNotExist) {

         return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                 .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                     var chatId =
                            String.format("%s_%s", senderId, recipientId);

                ChatRoom senderRecipient = new ChatRoom(UUID.randomUUID().toString(), chatId, senderId, recipientId);
                ChatRoom recipientSender = new ChatRoom(UUID.randomUUID().toString(), chatId, recipientId, senderId);
                chatRoomRepository.save(senderRecipient);
                chatRoomRepository.save(recipientSender);

                return Optional.of(chatId);
        });
    }
}
