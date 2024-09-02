package com.example.gaffer.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gaffer.models.RegisterRequestDTO;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.UserMailService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserEntityRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMailService userMailService;

    @Autowired
    public UserController(UserEntityRepository repository, PasswordEncoder passwordEncoder, UserMailService userMailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userMailService = userMailService;
    }

    @PostMapping
    @Transactional
    public void register(@RequestBody RegisterRequestDTO request) {
        UserEntity entity = new UserEntity();
        entity.setUsername(request.getEmail());
        entity.setEnabled(false);
        entity.setVerificationCode(RandomStringUtils.randomAlphanumeric(32));
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(entity);
        userMailService.sendVerificationMail(entity);
    }
}
