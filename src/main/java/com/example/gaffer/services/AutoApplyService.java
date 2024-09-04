package com.example.gaffer.services;

import java.util.List;

import com.example.gaffer.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AutoApplyService {

    @Autowired
    private LocationRepository locationRepository;

    // public List<String> getAllLocationNames() {
    //     return locationRepository.findAllLocations();
    // }
}
