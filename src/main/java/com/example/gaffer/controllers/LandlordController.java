package com.example.gaffer.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.gaffer.models.Listing;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.services.AutoRentService;

@Controller
public class LandlordController {

    ListingRepository listingRepository;
    AutoRentService autoService;

    public LandlordController(ListingRepository listingRepository, AutoRentService autoService){
        this.listingRepository=listingRepository;
        this.autoService=autoService;
    }
    
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }

    @GetMapping("/listing-management")
    public String getListingManagement(Model model, Authentication authentication){
        List<Listing> listings = listingRepository.findByUserId(String.valueOf(((UserEntity) authentication.getPrincipal()).getId()));
        model.addAttribute("listings", listings);
        return "listing-management";
    }

    @GetMapping("/listings/create")
    public String getCreateListing(Model model){
        model.addAttribute("listing", new Listing());
        return "create-listing";
    }

    @PostMapping("/listings/create")
    public String createListing(@ModelAttribute("listing") Listing listing, Authentication authentication, Model model){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        listing.setId(String.valueOf(listingRepository.count()+1));
        listing.setUserId(String.valueOf(user.getId()));

        listingRepository.save(listing);
        
        return "redirect:/listing-management";
    }
}
