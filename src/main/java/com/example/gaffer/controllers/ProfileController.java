package com.example.gaffer.controllers;

import java.util.Set;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

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
        if (entity.getIdDocument() != null) {
            String base64IdDocument = Base64.getEncoder().encodeToString(entity.getIdDocument());
            model.addAttribute("base64IdDocument", base64IdDocument);
        }

        if (entity.getWorkReference() != null) {
            String base64WorkReference = Base64.getEncoder().encodeToString(entity.getWorkReference());
            model.addAttribute("base64WorkReference", base64WorkReference);
        }

        if (entity.getLandlordReference() != null) {
            String base64LandlordReference = Base64.getEncoder().encodeToString(entity.getLandlordReference());
            model.addAttribute("base64LandlordReference", base64LandlordReference);
        }

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

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute("user") UserEntity updatedUser,
                                Authentication authentication,
                                @RequestParam("idDocument") MultipartFile idDocumentFile,
                                @RequestParam("workReference") MultipartFile workReferenceFile,
                                @RequestParam("landlordReference") MultipartFile landlordReferenceFile) throws IOException {
        
        UserEntity entity = (UserEntity) authentication.getPrincipal();

        if (!idDocumentFile.isEmpty()) {
            entity.setIdDocument(idDocumentFile.getBytes());
        }
        if (!workReferenceFile.isEmpty()) {
            entity.setWorkReference(workReferenceFile.getBytes());
        }
        if (!landlordReferenceFile.isEmpty()) {
            entity.setLandlordReference(landlordReferenceFile.getBytes());
        }

        userService.updateUserProfile(entity, updatedUser);

        return "redirect:/profile";
    }
}

