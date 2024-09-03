package com.example.gaffer.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.gaffer.config.EmailComponent;
import com.example.gaffer.models.RegisterRequestDTO;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.UserMailService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private EmailComponent emailComponent;
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
        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setEnabled(false);
        entity.setAccountNonExpired(false);
        entity.setAccountNonLocked(false);
        entity.setCredentialsNonExpired(false);
        entity.setVerificationCode(RandomStringUtils.randomAlphanumeric(32));
        if(emailComponent.getEnabled().equals("false")){
            entity.setEnabled(true);
            entity.setAccountNonExpired(true);
            entity.setAccountNonLocked(true);
            entity.setCredentialsNonExpired(true);
            entity.setRoles(new ArrayList<>(List.of("ROLE_ADMIN")));
            entity.setVerificationCode(null);
            repository.save(entity);
        } else {
            repository.save(entity);
            userMailService.sendVerificationMail(entity);
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/verify")
    @Transactional
    public String verifyAccount(@RequestParam("code") String code){
        System.out.println(code);
        System.out.println("User trying to verify");
        Optional<UserEntity> optionalEntity = repository.findByVerificationCode(code);
        if(!optionalEntity.isPresent()){
            return "login?error";
        }
        UserEntity entity = optionalEntity.get();
        entity.setEnabled(true);
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setRoles(new ArrayList<>(List.of("ROLE_ADMIN")));
        entity.setVerificationCode(null);
        repository.save(entity);
        return "home";
    }

}
