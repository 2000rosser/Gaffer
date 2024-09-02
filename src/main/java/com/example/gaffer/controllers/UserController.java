package com.example.gaffer.controllers;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.gaffer.models.RegisterRequestDTO;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.UserMailService;

import jakarta.transaction.Transactional;

@Controller
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
    public String register(@ModelAttribute RegisterRequestDTO request) {
        UserEntity entity = new UserEntity();
        entity.setUsername(request.getEmail());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setEnabled(true);
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setRoles(List.of("ROLE_ADMIN"));
        entity.setVerificationCode(RandomStringUtils.randomAlphanumeric(32));
        repository.save(entity);
        userMailService.sendVerificationMail(entity);
        return "redirect:/login?registered";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
