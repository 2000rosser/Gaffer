package com.example.gaffer.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.gaffer.repositories.LocationRepository;

@Service
public class AutoRentService {

    LocationRepository repository;

    public AutoRentService(LocationRepository repository){
        this.repository=repository;
    }

    public List<String> getBigName(String term, Pageable pageable){
        return repository.findByNameContaining(term, pageable);
    }
}
