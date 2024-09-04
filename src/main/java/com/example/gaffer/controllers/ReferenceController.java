package com.example.gaffer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gaffer.models.Reference;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.services.UserService;

import java.util.Optional;

import org.springframework.security.core.Authentication;

@Controller
public class ReferenceController {

    private final UserService userService;

    public ReferenceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add-reference")
    public String showAddReferenceForm(Model model) {
        model.addAttribute("reference", new Reference());
        return "add-reference";
    }

    @PostMapping("/add-reference")
    public String addReference(@ModelAttribute Reference reference, Authentication authentication) {
        UserEntity dtoObject = (UserEntity) authentication.getPrincipal();
        Optional<UserEntity> optionalEntity = userService.findUserByUsername(dtoObject.getUsername());
        if (!optionalEntity.isPresent()) {
            return "error";
        }
        UserEntity entity = optionalEntity.get();
        userService.addReferenceToUser(entity, reference);
        return "redirect:/profile";
    }
}

