package com.example.gaffer.controllers;

import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

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

import com.example.gaffer.models.ReferenceRequestDTO;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.models.Listing;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.services.UserService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Controller
public class ProfileController {

    @Autowired
    private S3Client s3Client;
    private static final String BUCKET_NAME = "my-local-bucket"; 

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

        if (entity.getIdDocument() != null && !entity.getIdDocument().isEmpty()) {
            model.addAttribute("idDocumentUrls", entity.getIdDocument());  // List of URLs
        }

        if (entity.getWorkReference() != null && !entity.getWorkReference().isEmpty()) {
            model.addAttribute("workReferenceUrls", entity.getWorkReference());
        }

        if (entity.getLandlordReference() != null && !entity.getLandlordReference().isEmpty()) {
            model.addAttribute("landlordReferenceUrls", entity.getLandlordReference());
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
                                @RequestParam("idDoc") MultipartFile idDocumentFile,
                                @RequestParam("workDoc") MultipartFile workReferenceFile,
                                @RequestParam("landDoc") MultipartFile landlordReferenceFile) throws IOException {

        UserEntity entity = (UserEntity) authentication.getPrincipal();

        if (!idDocumentFile.isEmpty()) {
            String idDocumentUrl = uploadFileToS3(idDocumentFile, "idDocument_" + UUID.randomUUID());
            if(entity.getIdDocument()==null) entity.setIdDocument(new ArrayList<>(List.of(idDocumentUrl)));
            else entity.getIdDocument().add(idDocumentUrl);
        }

        if (!workReferenceFile.isEmpty()) {
            String workReferenceUrl = uploadFileToS3(workReferenceFile, "workReference_" + UUID.randomUUID());
            if(entity.getWorkReference()==null) entity.setWorkReference(new ArrayList<>(List.of(workReferenceUrl)));
            else entity.getWorkReference().add(workReferenceUrl);
        }

        if (!landlordReferenceFile.isEmpty()) {
            String landlordReferenceUrl = uploadFileToS3(landlordReferenceFile, "landlordReference_" + UUID.randomUUID());
            if(entity.getLandlordReference()==null) entity.setLandlordReference(new ArrayList<>(List.of(landlordReferenceUrl)));
            else entity.getLandlordReference().add(landlordReferenceUrl);
        }

        userService.updateUserProfile(entity, updatedUser);

        return "redirect:/profile";
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

