package com.example.gaffer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {

    @Value("${email.verification}")
    private String enabled;

    public String getEnabled() {
        return this.enabled;
    }
    
}
