package com.example.gaffer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.gaffer.models.Listing;

public interface ListingRepository extends JpaRepository<Listing, String> {
    
    List<Listing> findByUserId(@Param("name") String name);
}
