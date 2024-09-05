package com.example.gaffer.controllers;

import java.util.List;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.gaffer.models.AutoServiceDTO;
import com.example.gaffer.models.Listing;
import com.example.gaffer.services.AutoRentService;
import com.fasterxml.jackson.core.JsonProcessingException;



@Controller
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
        System.out.println("Response reveived\n" + response.getBody());
        List<Listing> result = autoService.generateListings(response);

        model.addAttribute("listings", result);
        model.addAttribute("autoDto", new AutoServiceDTO());
        
        return "auto-rent";
    }
}
