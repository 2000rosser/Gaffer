package com.example.gaffer.controllers;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gaffer.models.ReferenceRequestDTO;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.models.Listing;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.services.UserService;

@Controller
public class ProfileController {

    UserService userService;
    ListingRepository listingRepository;

    public ProfileController(UserService userService, ListingRepository listingRepository){
        this.userService=userService;
        this.listingRepository=listingRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        UserEntity entity = (UserEntity) authentication.getPrincipal();
        ReferenceRequestDTO profile = userService.getUserProfile(entity.getId());
        model.addAttribute("user", profile);
        return "profile";
    }
    
    @GetMapping("/profile/{id}")
    public String publicProfile(Model model, Authentication authentication, @PathVariable(required=true,name="id") String id) {
        UserEntity entity = (UserEntity) authentication.getPrincipal();
        Set<String> applications = entity.getApplications();
        List<Listing> listings = new ArrayList<>();
        for(String value : applications) listings.add(listingRepository.getReferenceById(value));
        boolean allowed=false;
        for(Listing listing : listings) {
            if(listing.getUserId().equals(String.valueOf(entity.getId()))){
                allowed=true;
            }
        }
        if(allowed==false) return "home";

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

