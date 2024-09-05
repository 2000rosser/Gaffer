package com.example.gaffer.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gaffer.models.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {

    @Query("SELECT l.name FROM LocationEntity l WHERE LOWER(l.name) LIKE %:term%")
    List<String> findByNameContaining(@Param("term") String term, Pageable pageable);

    @Query("SELECT l.bigName FROM LocationEntity l WHERE l.bigName LIKE %:term%")
    List<String> findByBigNameContaining(@Param("term") String term, Pageable pageable);

    @Query("SELECT l FROM LocationEntity l WHERE TRIM(l.name) = TRIM(:name)")
    LocationEntity findByName(@Param("name") String name);
}
