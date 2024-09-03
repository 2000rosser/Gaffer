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

        List<String> currentEntry = new ArrayList<>();
        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.endsWith("),")) {
                currentEntry.add(line);
                processEntry(currentEntry);
                currentEntry.clear();
            } else {
                currentEntry.add(line);
            }
        }

        System.out.println("Locations have been imported successfully.");
    }

    private void processEntry(List<String> entryLines) {
        if (entryLines.size() < 4) {
            System.err.println("Invalid entry found, skipping: " + entryLines);
            return;
        }

        try {
            String code = entryLines.get(1).replace("\"", "").trim();
            String name = entryLines.get(2).replace("\"", "").trim();
            String slug = entryLines.get(3).replace("\"", "").replace("),", "").trim();

            LocationEntity location = new LocationEntity();
            location.setCode(code.replaceAll(",", ""));
            location.setName(name);
            location.setSlug(slug);

            locationEntityRepository.save(location);

        } catch (Exception e) {
            System.err.println("Failed to process entry: " + entryLines + ", due to: " + e.getMessage());
        }
    }
}

