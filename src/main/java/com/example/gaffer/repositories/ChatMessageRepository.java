package com.example.gaffer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gaffer.models.ChatMessage;
import com.example.gaffer.models.MessageStatus;

import jakarta.transaction.Transactional;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, String> {

    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage c SET c.status = :status WHERE c.senderId = :senderId AND c.recipientId = :recipientId")
    void updateStatusBySenderIdAndRecipientId(@Param("senderId") String senderId, @Param("recipientId") String recipientId, @Param("status") MessageStatus status);


    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);
}
