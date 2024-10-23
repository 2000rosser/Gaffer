package com.example.gaffer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gaffer.models.SavedSearch;

public interface SavedSearchRepository extends JpaRepository<SavedSearch, Long> {
    


}

