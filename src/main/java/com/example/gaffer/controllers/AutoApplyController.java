package com.example.gaffer.controllers;

import java.util.List;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.example.gaffer.models.AutoServiceDTO;
import com.example.gaffer.models.Listing;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.services.AutoRentService;
import com.fasterxml.jackson.core.JsonProcessingException;



@Controller
@SessionAttributes("listings")
public class AutoApplyController {
    
    @Autowired
    AutoRentService autoService;

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

    @PostMapping("/add-auto")
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

    @PostMapping("/apply")
    public ResponseEntity<String> applyToRent(@ModelAttribute Listing listing, Authentication authentication, Model model, @SessionAttribute("listings") List<Listing> listings) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        HttpEntity<String> craftedRequest = autoService.createApplication(listing, userEntity);
        String url = "https://gateway.daft.ie/old/v1/reply";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, craftedRequest, String.class);
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
                return ResponseEntity.ok("Failed");
            }
        } catch (Exception e) {
            model.addAttribute("listings", listings);
            model.addAttribute("autoDto", new AutoServiceDTO());
            return ResponseEntity.ok("Error");
        }
        
    }
}
