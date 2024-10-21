package com.example.gaffer.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.gaffer.models.ChatMessage;
import com.example.gaffer.models.UserDto;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.services.ChatMessageService;
import com.example.gaffer.services.ChatRoomService;
import com.example.gaffer.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class ChatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ChatMessageService chatMessageService;

    @MockBean
    private ChatRoomService chatRoomService;

    @MockBean
    private UserService userService;

    @MockBean
    private Authentication authentication;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserEntity userDetails;
    private UserEntity userDetails2;

    @BeforeEach
    public void setUp() {
        userDetails = new UserEntity();
        userDetails.setId(1L);
        userDetails.setUsername("a@a.com");
        userDetails.setDescription("Hi, I am interested in renting your property. Please let me know what you require and I'll provide it.");
        userDetails.setPhoneNumber("0872970140");
        userDetails.setName("Ross Murphy");
        userDetails.setPassword(passwordEncoder.encode("a"));
        userDetails.setOccupation("Software Engineer");
        userDetails.setProfilePicture("/images/default_user_profile_picture.png");
        userDetails.setEnabled(true);
        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setRoles(new ArrayList<>(List.of("ROLE_ADMIN")));

        userDetails2 = new UserEntity();
        userDetails2.setId(2L);
        userDetails2.setUsername("b@b.com");
        userDetails2.setDescription("Hi, I am interested in renting your property. Please let me know what you require and I'll provide it.");
        userDetails2.setPhoneNumber("0872970140");
        userDetails2.setName("Ross Murphy");
        userDetails2.setPassword(passwordEncoder.encode("a"));
        userDetails2.setOccupation("Software Engineer");
        userDetails2.setProfilePicture("/images/default_user_profile_picture.png");
        userDetails2.setEnabled(true);
        userDetails2.setAccountNonExpired(true);
        userDetails2.setAccountNonLocked(true);
        userDetails2.setCredentialsNonExpired(true);
        userDetails2.setRoles(new ArrayList<>(List.of("ROLE_ADMIN")));
    }

    @Test
    public void testOpenChat() throws Exception {
        when(userService.getUserProfileWithEntity(userDetails)).thenReturn(new UserDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/open-chat")
                    .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("chat"));

        verify(userService, times(1)).getUserProfileWithEntity(userDetails);
    }

    @Test
    public void testOpenChatWithUser() throws Exception {
        String recipientId = "2";
        UserEntity user = userDetails;
        when(userService.getUserProfileWithEntity(userDetails)).thenReturn(new UserDto(user.getId(), user.getName(), user.getPhoneNumber(), user.getLocation(),
        user.getDescription(), user.getOccupation(), user.getUsername(), user.getPlaceOfWork(),
        user.getProfilePicture(), user.getIdDoc(), user.getWorkDoc(), user.getLandDoc()));
        mockMvc.perform(MockMvcRequestBuilders.get("/open-chat/{recipientId}", recipientId).with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("chat"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(userDetails.getId());

        when(userService.getUserProfileWithEntity(userDetails)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/current-user").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDetails.getId()));

    }


    @Test
    public void testFindChatMessages() throws Exception {
        String senderId = "1";
        String recipientId = "2";
        
        ChatMessage message1 = new ChatMessage();
        message1.setSenderId(senderId);
        message1.setRecipientId(recipientId);
        message1.setContent("Hello");
        message1.setId(UUID.randomUUID().toString());
        
        ChatMessage message2 = new ChatMessage();
        message2.setSenderId(recipientId);
        message2.setRecipientId(senderId);
        message2.setContent("Hi");
        message2.setId(UUID.randomUUID().toString());
        
        List<ChatMessage> mockChatMessages = Arrays.asList(message1, message2);
        
        when(chatMessageService.findChatMessages(senderId, recipientId)).thenReturn(mockChatMessages);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/{senderId}/{recipientId}", senderId, recipientId)
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].content").value("Hello"))
                .andExpect(jsonPath("$[1].content").value("Hi"));
        
        verify(chatMessageService, times(1)).findChatMessages(senderId, recipientId);
    }


    @Test
    public void testFindMessageById() throws Exception {
        String messageId = "1";
        ChatMessage message = new ChatMessage();
        message.setId(messageId);
        when(chatMessageService.findById(messageId)).thenReturn(message);

        mockMvc.perform(MockMvcRequestBuilders.get("/messages/{id}", messageId).with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(messageId));
    }

    @Test
    public void testCountNewMessages() throws Exception {
        String senderId = "1";
        String recipientId = "2";
        when(chatMessageService.countNewMessages(senderId, recipientId)).thenReturn(5L);

        mockMvc.perform(MockMvcRequestBuilders.get("/messages/{senderId}/{recipientId}/count", senderId, recipientId).with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void testFindMessagesByRecipient() throws Exception {
        String senderId = "1";
        String recipientId = "2";
        
        ChatMessage message1 = new ChatMessage();
        message1.setSenderId(senderId);
        message1.setRecipientId(recipientId);
        message1.setContent("Hello");
        message1.setId(UUID.randomUUID().toString());
        
        ChatMessage message2 = new ChatMessage();
        message2.setSenderId(recipientId);
        message2.setRecipientId(senderId);
        message2.setContent("Hi");
        message2.setId(UUID.randomUUID().toString());
        
        List<ChatMessage> mockChatMessages = Arrays.asList(message1, message2);
        when(chatMessageService.findChatMessages(senderId, recipientId)).thenReturn(mockChatMessages);

        mockMvc.perform(MockMvcRequestBuilders.get("/messages").param("recipientId", recipientId).with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].content").value("Hello"))
                .andExpect(jsonPath("$[1].content").value("Hi"));
    }
}
