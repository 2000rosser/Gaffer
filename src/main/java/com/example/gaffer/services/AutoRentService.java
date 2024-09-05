package com.example.gaffer.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.example.gaffer.models.AutoServiceDTO;
import com.example.gaffer.models.Filter;
import com.example.gaffer.models.GeoFilter;
import com.example.gaffer.models.Listing;
import com.example.gaffer.models.Paging;
import com.example.gaffer.models.Range;
import com.example.gaffer.repositories.LocationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AutoRentService {

    LocationRepository repository;

    public AutoRentService(LocationRepository repository){
        this.repository=repository;
    }

    public List<String> getBigName(String term, Pageable pageable){
        return repository.findByNameContaining(term, pageable);
    }

    public HttpEntity<String> createListingRequest(AutoServiceDTO autoDto) throws JsonProcessingException{

        Listing listingRequest = new Listing();
        listingRequest.setSection("residential-to-rent");

        List<Filter> filters = new ArrayList<>();

        Filter propertyFilter = new Filter();
        propertyFilter.setName("propertyType");
        propertyFilter.setValues(List.of(autoDto.getPropertyType()));
        filters.add(propertyFilter);
        
        Filter furnishFilter = new Filter();
        if(!autoDto.getFurnishing().equals("ANY")) {
            furnishFilter.setName("furnishing");
            furnishFilter.setValues(List.of(autoDto.getFurnishing()));
            filters.add(furnishFilter);
        }
        listingRequest.setFilters(filters);
        
        System.out.println(autoDto.getLocations().get(0));
        System.out.println(repository.findByName(autoDto.getLocations().get(0)).getCode());
        GeoFilter geoFilter = new GeoFilter();
        geoFilter.setStoredShapeIds(List.of(repository.findByName(autoDto.getLocations().get(0)).getCode()));
        geoFilter.setGeoSearchType("STORED_SHAPES");
        listingRequest.setGeoFilter(geoFilter);

        Range numBedsRange = new Range();
        numBedsRange.setName("numBeds");
        numBedsRange.setFrom(autoDto.getMinBeds());
        numBedsRange.setTo(autoDto.getMaxBeds());

        Range priceRange = new Range();
        priceRange.setName("rentalPrice");
        priceRange.setFrom(autoDto.getMinPrice());
        priceRange.setTo(autoDto.getMaxPrice());

        listingRequest.setRanges(Arrays.asList(numBedsRange, priceRange));

        Paging paging = new Paging();
        paging.setFrom("0");
        paging.setPagesize("50");
        listingRequest.setPaging(paging);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(listingRequest);

        System.out.println("Payload Crafted\n" + jsonPayload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15");
        headers.add("brand", "daft");
        headers.add("platform", "web");

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
        return entity;
    }
}
