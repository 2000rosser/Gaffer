package com.example.gaffer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ComponentProperties {

    @Value("${email.verification}")
    private String enabled;

    @Value("${autoapply}")
    private String autoApply;

    public String getEnabled() {
        return this.enabled;
    }
    
    public String getAutoApply() {
        return this.autoApply;
    }
}
