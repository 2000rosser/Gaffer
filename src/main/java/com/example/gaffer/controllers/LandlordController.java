package com.example.gaffer.controllers;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.gaffer.models.Listing;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.services.AutoRentService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.S3Client;

@Controller
public class LandlordController {

    @Autowired
    S3Client s3Client;
    private static final String BUCKET_NAME = "my-local-bucket"; 

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
    public String createListing(@ModelAttribute("listing") Listing listing, Authentication authentication, Model model, 
                                @RequestParam("imageFiles[]") MultipartFile[] imageFiles){
            
        UserEntity user = (UserEntity) authentication.getPrincipal();
        if (imageFiles != null && imageFiles.length > 0) {
            Arrays.stream(imageFiles).filter(file -> !file.isEmpty()).forEach(file -> {
                try {
                    String imageUrl = uploadFileToS3(file, "listingImage_" + UUID.randomUUID());
                    if (listing.getImages() == null) {
                        listing.setImages(new ArrayList<>(List.of(imageUrl)));
                    } else {
                        listing.getImages().add(imageUrl);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to upload ID document: " + e.getMessage());
                }
            });
        }
        listing.setId(String.valueOf(listingRepository.count()+1));
        listing.setUserId(String.valueOf(user.getId()));
        listing.setApplications(new HashSet<String>());

        listingRepository.save(listing);
        
        return "redirect:/listing-management";
    }

    @GetMapping("/view-applications/{id}")
    public String viewApplications(Model model, @PathVariable(required=true, name="id") String id){

        Listing listing = listingRepository.getReferenceById(id);
        List<String> users = new ArrayList<>();
        for(String user : listing.getApplications()) users.add(user);

        model.addAttribute("users", users);
        return "view-applications";
    }

    private String uploadFileToS3(MultipartFile file, String keyPrefix) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
    
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
    
        String key = keyPrefix + fileExtension;
    
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(key)
                        .build(),
                RequestBody.fromBytes(file.getBytes()));
    
        return String.format("http://localhost:4566/%s/%s", BUCKET_NAME, key);
    }
}
