package com.example.gaffer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gaffer.models.Listing;

public interface ListingRepository extends JpaRepository<Listing, String> {

}
