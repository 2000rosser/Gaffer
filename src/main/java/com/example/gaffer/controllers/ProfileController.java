package com.example.gaffer.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import com.example.gaffer.models.UserDto;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.repositories.UserEntityRepository;
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
    @Autowired UserEntityRepository userRepository;

    public ProfileController(UserService userService, ListingRepository listingRepository){
        this.userService=userService;
        this.listingRepository=listingRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        UserEntity entity = (UserEntity) authentication.getPrincipal();
        UserDto profile = userService.getUserProfile(entity.getId());
        model.addAttribute("user", profile);

        if (entity.getIdDocument() != null && !entity.getIdDocument().isEmpty()) {
            model.addAttribute("idDocumentUrls", entity.getIdDocument());
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
        List<Listing> listings = listingRepository.findByUserId(String.valueOf(entity.getId()));
        boolean allowed=false;
        for(Listing listing : listings){
            if(listing.getApplications().contains(id)){
                allowed = true;
            }
        }
        if(allowed==false) return "home";

        UserDto profile = userService.getUserProfile(Long.valueOf(id));
        model.addAttribute("user", profile);
        if (entity.getIdDocument() != null && !entity.getIdDocument().isEmpty()) {
            model.addAttribute("idDocumentUrls", entity.getIdDocument());
        }

        if (entity.getWorkReference() != null && !entity.getWorkReference().isEmpty()) {
            model.addAttribute("workReferenceUrls", entity.getWorkReference());
        }

        if (entity.getLandlordReference() != null && !entity.getLandlordReference().isEmpty()) {
            model.addAttribute("landlordReferenceUrls", entity.getLandlordReference());
        }
        return "profile-view";
    }

    @GetMapping("/edit-profile")
    public String showEditProfile(Model model, Authentication authentication) {
        UserEntity authEntity = (UserEntity) authentication.getPrincipal();
        UserEntity entity = userRepository.findById(authEntity.getId()).get();
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
                                @RequestParam("profilePic") MultipartFile profilePicture,
                                @RequestParam("idDoc[]") MultipartFile[] idDocumentFile,
                                @RequestParam("workDoc[]") MultipartFile[] workReferenceFile,
                                @RequestParam("landDoc[]") MultipartFile[] landlordReferenceFile,
                                @RequestParam("fromListing") String fromListing) throws IOException {

        UserEntity entity = (UserEntity) authentication.getPrincipal();

        if (profilePicture != null) {
            try {
                String idDocumentUrl = uploadFileToS3(profilePicture, "profilePicture_" + UUID.randomUUID());
                entity.setProfilePicture(idDocumentUrl);
            } catch (Exception e) {
                System.err.println("Failed to upload ID document: " + e.getMessage());
            }
        }
        if (idDocumentFile != null && idDocumentFile.length > 0) {
            Arrays.stream(idDocumentFile).filter(file -> !file.isEmpty()).forEach(file -> {
                try {
                    String idDocumentUrl = uploadFileToS3(file, "idDocument_" + UUID.randomUUID());
                    if (entity.getIdDocument() == null) {
                        entity.setIdDocument(new ArrayList<>(List.of(idDocumentUrl)));
                    } else {
                        entity.getIdDocument().add(idDocumentUrl);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to upload ID document: " + e.getMessage());
                }
            });
        }

        if (workReferenceFile != null && workReferenceFile.length > 0) {
            Arrays.stream(workReferenceFile).filter(file -> !file.isEmpty()).forEach(file -> {
                try {
                    String workReferenceUrl = uploadFileToS3(file, "workReference_" + UUID.randomUUID());
                    if (entity.getWorkReference() == null) {
                        entity.setWorkReference(new ArrayList<>(List.of(workReferenceUrl)));
                    } else {
                        entity.getWorkReference().add(workReferenceUrl);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to upload work reference: " + e.getMessage());
                }
            });
        }

        if (landlordReferenceFile != null && landlordReferenceFile.length > 0) {
            Arrays.stream(landlordReferenceFile).filter(file -> !file.isEmpty()).forEach(file -> {
                try {
                    String landlordReferenceUrl = uploadFileToS3(file, "landlordReference_" + UUID.randomUUID());
                    if (entity.getLandlordReference() == null) {
                        entity.setLandlordReference(new ArrayList<>(List.of(landlordReferenceUrl)));
                    } else {
                        entity.getLandlordReference().add(landlordReferenceUrl);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to upload landlord reference: " + e.getMessage());
                }
            });
        }

        userService.updateUserProfile(entity, updatedUser);
        if(fromListing!=null && !fromListing.equals("") && !fromListing.equals("none")) return "redirect:/listing/" + fromListing;

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

