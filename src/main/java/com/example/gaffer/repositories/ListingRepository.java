package com.example.gaffer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gaffer.models.Listing;

public interface ListingRepository extends JpaRepository<Listing, String> {
    
    List<Listing> findByUserId(@Param("id") String id);

    @Query("SELECT l FROM Listing l WHERE " +
       "(:location IS NULL OR l.seoTitle = :location) " +
       "AND (:minPrice IS NULL OR l.price >= :minPrice) " +
       "AND (:maxPrice IS NULL OR l.price <= :maxPrice) " +
       "AND (:minBeds IS NULL OR l.numBedrooms >= :minBeds) " +
       "AND (:maxBeds IS NULL OR l.numBedrooms <= :maxBeds) " +
       "AND (:propertyType IS NULL OR l.propertyType = :propertyType OR :propertyType = 'ANY') " +
       "AND (:furnishing IS NULL OR l.category = :furnishing OR :furnishing = 'Any')")
    List<Listing> searchListings(
        @Param("location") String location,
        @Param("minPrice") Integer minPrice,
        @Param("maxPrice") Integer maxPrice,
        @Param("minBeds") Integer minBeds,
        @Param("maxBeds") Integer maxBeds,
        @Param("propertyType") String propertyType,
        @Param("furnishing") String furnishing);


}
