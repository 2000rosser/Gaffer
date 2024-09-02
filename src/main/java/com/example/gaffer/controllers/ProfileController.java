package com.example.gaffer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;

@Controller
public class ProfileController {

    UserEntityRepository repository;

    public ProfileController(UserEntityRepository repository){
        this.repository=repository;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        UserEntity entity = (UserEntity) authentication.getPrincipal();
        model.addAttribute("user", entity);
        return "profile";
    }

    @GetMapping("/edit-profile")
    public String showEditProfile(Model model, Authentication authentication) {
        UserEntity entity = (UserEntity) authentication.getPrincipal();
        model.addAttribute("user", entity);
        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute("user") UserEntity updatedUser, Authentication authentication) {
        UserEntity entity = (UserEntity) authentication.getPrincipal();
        entity.setName(updatedUser.getName());
        entity.setUsername(updatedUser.getUsername());
        entity.setLocation(updatedUser.getLocation());
        entity.setDescription(updatedUser.getDescription());

        repository.save(entity);

        return "redirect:/profile";
    }
}

