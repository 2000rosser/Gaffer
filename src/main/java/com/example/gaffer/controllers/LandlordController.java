package com.example.gaffer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandlordController {
    
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }

}
