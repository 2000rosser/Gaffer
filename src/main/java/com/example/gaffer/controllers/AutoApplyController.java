package com.example.gaffer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.gaffer.repositories.LocationRepository;
import com.example.gaffer.services.AutoApplyService;



@Controller
public class AutoApplyController {
    
    @Autowired
    AutoApplyService autoService;

    @Autowired
    LocationRepository repository;

    @GetMapping("auto-rent")
    public String getAutoRent(){
        return "auto-rent";
    }

    @GetMapping("/autocomplete-auto")
    @ResponseBody
    public List<String> getLocations(@RequestParam("term") String term) {
        System.out.println("Retrieving locations");
        Pageable limit = PageRequest.of(0, 5);
        List<String> result = repository.findByNameContaining(term.toLowerCase(), limit);
        System.out.println(result.size());
        return result;
    }
}
