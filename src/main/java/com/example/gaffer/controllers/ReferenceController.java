package com.example.gaffer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gaffer.models.Reference;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;

import java.util.Optional;

import org.springframework.security.core.Authentication;

@Controller
public class ReferenceController {

    UserEntityRepository repository;

    public ReferenceController(UserEntityRepository repository){
        this.repository=repository;
    }

    @GetMapping("/add-reference")
    public String showAddReferenceForm(Model model) {
        model.addAttribute("reference", new Reference());
        return "add-reference";
    }

    @PostMapping("/add-reference")
    public String addReference(@ModelAttribute Reference reference, Authentication authentication) {
        UserEntity dtoObject = (UserEntity) authentication.getPrincipal();
        Optional<UserEntity> optionalEntity = repository.findByUsername(dtoObject.getUsername());
        if(!optionalEntity.isPresent()){
            return "error";
        }
        UserEntity entity = optionalEntity.get();
        entity.getReferences().add(reference);
        repository.save(entity);
        return "redirect:/profile";
    }
}

