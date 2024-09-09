package com.example.gaffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Gaffer {

    public static void main(String[] args) {
        SpringApplication.run(Gaffer.class, args);
    }
}