package com.example.gaffer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gaffer.models.ReferenceRequestDTO;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.services.UserService;

@Controller
public class ProfileController {

    UserService userService;

    public ProfileController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        UserEntity entity = (UserEntity) authentication.getPrincipal();
        ReferenceRequestDTO profile = userService.getUserProfile(entity.getId());
        model.addAttribute("user", profile);
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
        userService.updateUserProfile(entity, updatedUser);
        return "redirect:/profile";
    }
}

