package com.example.gaffer.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.gaffer.components.ComponentProperties;
import com.example.gaffer.models.AutoServiceDTO;
import com.example.gaffer.models.Listing;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.AutoRentService;
import com.fasterxml.jackson.core.JsonProcessingException;



@Controller
@SessionAttributes("listings")
public class AutoApplyController {
    
    private final AutoRentService autoService;

    private final ComponentProperties properties;

    private final UserEntityRepository userRepository;

    public AutoApplyController(AutoRentService autoService, ComponentProperties properties, UserEntityRepository userRepository) {
        this.autoService = autoService;
        this.properties = properties;
        this.userRepository = userRepository;
    }

    @GetMapping("auto-rent")
    public String getAutoRent(Model model){
        model.addAttribute("autoDto", new AutoServiceDTO());
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
    public String fetchListings(@ModelAttribute AutoServiceDTO autoDto, Model model) throws JsonProcessingException, ParseException{
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Request reveived");
        HttpEntity<String> entity = autoService.createListingRequest(autoDto);

        String url = "https://gateway.daft.ie/old/v1/listings";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        List<Listing> result = autoService.generateListings(response);

        model.addAttribute("listings", result);
        model.addAttribute("autoDto", new AutoServiceDTO());
        
        return "auto-rent";
    }

    @PostMapping("/add-auto")
    public String addAutoApply(@ModelAttribute AutoServiceDTO autoDto, Authentication authentication, Model model) throws ClientProtocolException, IOException, ParseException {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        user.setAutoEnabled(true);
        List<AutoServiceDTO> currentServices = user.getAutoservices();
        if (currentServices == null || currentServices.isEmpty()) {
            user.setAutoservices(new ArrayList<>(List.of(autoDto)));
        } else {
            currentServices.add(autoDto);
            user.setAutoservices(currentServices);
        }
        System.out.println(user.toString());
        userRepository.save(user);

        System.out.println(autoDto.toString());

        model.addAttribute("autoDto", new AutoServiceDTO());

        // scheduledTasks.dispatchAutoApplications();

        // return ResponseEntity.ok("Success");
        return "auto-rent";
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyToRent(@ModelAttribute Listing listing, Authentication authentication, Model model, @SessionAttribute("listings") List<Listing> listings) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        if(userEntity.getName()==null || userEntity.getPhoneNumber() == null || userEntity.getDescription() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please finish setting up your profile");
        }
        HttpEntity<String> craftedRequest = autoService.createApplication(listing, userEntity);
        String url = "https://gateway.daft.ie/old/v1/reply";
        // if (properties.getAutoApply().equals("true")){
            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(url, HttpMethod.POST, craftedRequest, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    model.addAttribute("listings", listings);
                    model.addAttribute("autoDto", new AutoServiceDTO());
                    return ResponseEntity.ok("Success");
                } else {
                    model.addAttribute("responseMessage", "Failed");
                    model.addAttribute("listings", listings);
                    model.addAttribute("autoDto", new AutoServiceDTO());
                    return ResponseEntity.status(response.getStatusCode()).body("Failed: " + response.getStatusCode());
                }
            } catch (RestClientException e) {
                model.addAttribute("listings", listings);
                model.addAttribute("autoDto", new AutoServiceDTO());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
            }
        // } else {
        //     return ResponseEntity.ok("Success");
        // }
    }
}
