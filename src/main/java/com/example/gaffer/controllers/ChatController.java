package com.example.gaffer.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.gaffer.models.ChatMessage;
import com.example.gaffer.models.ChatNotification;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.ChatMessageService;
import com.example.gaffer.services.ChatRoomService;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;
    
    @Autowired
    private UserEntityRepository userRepository;

    @GetMapping("/open-chat")
    public String openChat(Model model, Authentication authentication){
        model.addAttribute("currentUser", (UserEntity) authentication.getPrincipal());
        return "chat";
    }

    @GetMapping("/current-user")
    @ResponseBody
    public UserEntity getCurrentUser(Authentication authentication){
        return (UserEntity) authentication.getPrincipal(); 
    }

    @GetMapping("/users")
    @ResponseBody
    public List<UserEntity> getAllUsers(Authentication authentication){
        List<UserEntity> users = userRepository.findAll();
        UserEntity curr = (UserEntity) authentication.getPrincipal();
        Iterator<UserEntity> iterator = users.iterator();

        while (iterator.hasNext()) {
            UserEntity user = iterator.next();
            if (curr.getId().equals(user.getId())) {
                iterator.remove(); 
            }
        }
        
        return users;
    }
    
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());
        chatMessage.setId(UUID.randomUUID().toString());
        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable String id) throws Exception {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }

    @GetMapping("/messages")
        public ResponseEntity<?> findMessagesByRecipient(@RequestParam String recipientId, Authentication authentication) {
        String senderId = String.valueOf(((UserEntity) authentication.getPrincipal()).getId());
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
