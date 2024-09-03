package com.example.gaffer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gaffer.models.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
}
