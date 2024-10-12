package com.example.gaffer.controllers;

import java.util.List;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.gaffer.models.Listing;
import com.example.gaffer.models.ListingDTO;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.AutoRentService;
import com.fasterxml.jackson.core.JsonProcessingException;



@Controller
@SessionAttributes("listings")
public class SearchListings {
    
    private final AutoRentService autoService;
    private final ListingRepository listingRepository;
    private final UserEntityRepository userRepository;

    public SearchListings(AutoRentService autoService, ListingRepository listingRepository, UserEntityRepository userRepository) {
        this.autoService = autoService;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("auto-rent")
    public String getAutoRent(Model model){
        model.addAttribute("listingDto", new ListingDTO());
        return "auto-rent";
    }

    @GetMapping("/autocomplete-auto")
    @ResponseBody
    public List<String> getLocations(@RequestParam("term") String term) {
        Pageable limit = PageRequest.of(0, 5);
        List<String> result = autoService.getBigName(term.toLowerCase(), limit);
        return result;
    }

    @PostMapping("/search")
    public String fetchListings(@ModelAttribute ListingDTO listingDto, Model model) throws JsonProcessingException, ParseException{
        if(listingDto.getFurnishing().equals("ANY")) listingDto.setFurnishing(null);
        if(listingDto.getPropertyType().equals("Any")) listingDto.setPropertyType(null);
        List<Listing> listings = listingRepository.searchListings(listingDto.getLocation(), Integer.valueOf(listingDto.getMinPrice()), Integer.valueOf(listingDto.getMaxPrice()), 
        Integer.valueOf(listingDto.getMinBeds()), Integer.valueOf(listingDto.getMaxBeds()), listingDto.getPropertyType(), listingDto.getFurnishing());
        model.addAttribute("listings", listings);
        model.addAttribute("listingDto", new ListingDTO());
        
        return "auto-rent";
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyToRent(@ModelAttribute Listing listing, Authentication authentication, Model model, @SessionAttribute("listings") List<Listing> listings) throws JsonProcessingException {
        
        return ResponseEntity.ok("Success");        
    }
}
