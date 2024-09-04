package com.example.gaffer.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.gaffer.models.LocationEntity;
import com.example.gaffer.repositories.LocationRepository;

@Component
public class LocationDataImporter implements CommandLineRunner {

    @Autowired
    private LocationRepository locationEntityRepository;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("locations.txt");

        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            lines = reader.lines().collect(Collectors.toList());
        }

        for (String line : lines) {
            String[] splitLines = line.split(",");
            LocationEntity location = new LocationEntity();
            location.setBigName(splitLines[0].replace(",", ""));
            location.setCode(splitLines[1].replace(",", ""));
            location.setName(splitLines[2].replace(",", ""));
            location.setSlug(splitLines[3].replace(",", ""));
            locationEntityRepository.save(location);
        }

        System.out.println("Locations have been imported successfully.");
    }

}

