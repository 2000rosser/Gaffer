package com.example.gaffer.controllers;

import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gaffer.models.Listing;
import com.example.gaffer.models.UserEntity;

@Controller
public class LandlordController {
    
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }

    @GetMapping("/listing-management")
    public String getListingManagement(){
        return "listing-management";
    }

    @PostMapping("/listings/create")
    public int createListing(@ModelAttribute("listing") Listing listing, Authentication authentication){
        // TODO make a new listing object
        UserEntity user = (UserEntity) authentication.getPrincipal();
        listing.setId(String.valueOf(user.getId()));
        
        return HttpStatus.SC_OK;
    }

}
