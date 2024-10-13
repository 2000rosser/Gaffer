package com.example.gaffer.services;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.gaffer.models.Application;
import com.example.gaffer.models.AutoServiceDTO;
import com.example.gaffer.models.Filter;
import com.example.gaffer.models.GeoFilter;
import com.example.gaffer.models.Listing;
import com.example.gaffer.models.ListingRequest;
import com.example.gaffer.models.Paging;
import com.example.gaffer.models.Range;
import com.example.gaffer.models.UserEntity;
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

        ListingRequest listingRequest = new ListingRequest();
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15");
        headers.add("brand", "daft");
        headers.add("platform", "web");

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
        return entity;
    }



    public HttpEntity<String> createApplication(Listing listing, UserEntity user) throws JsonProcessingException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "gateway.daft.ie");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Connection", "keep-alive");
        headers.add("platform", "iOS");
        headers.add("Accept", "application/json");
        headers.add("brand", "daft");
        headers.add("Accept-Language", "en-GB,en;q=0.9");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("User-Agent", "Daft.ie/0 CFNetwork/1335.0.3 Darwin/21.6.0");

        Application application = new Application(user.getName(), true, user.getUsername(), user.getDescription(), Integer.valueOf(listing.getId()), user.getPhoneNumber());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(application);

        System.out.println(jsonPayload);

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
        return entity;
    }

    public HttpPost createApacheApplication(Listing listing, UserEntity user) throws JsonProcessingException, UnsupportedEncodingException{
        HttpPost request = new HttpPost("https://gateway.daft.ie/old/v1/reply");

        Application application = new Application(user.getName(), true, user.getUsername(), user.getDescription(), Integer.valueOf(listing.getId()), user.getPhoneNumber());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(application);

        StringEntity params = new StringEntity(jsonPayload);
        request.setEntity(params);
        request.setHeader("Host", "gateway.daft.ie");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("platform", "iOS");
        request.setHeader("Accept", "application/json");
        request.setHeader("brand", "daft");
        request.setHeader("Accept-Language", "en-GB,en;q=0.9");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("User-Agent", "Daft.ie/0 CFNetwork/1335.0.3 Darwin/21.6.0");
        return request;
    }

    public String createJsonListing(AutoServiceDTO autoDto) throws JsonProcessingException{

        ListingRequest listingRequest = new ListingRequest();
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

        Range dateRange = new Range();
        dateRange.setName("firstPublishDate");
        dateRange.setFrom("now-2h");
        dateRange.setTo("");

        listingRequest.setRanges(Arrays.asList(numBedsRange, priceRange, dateRange));

        Paging paging = new Paging();
        paging.setFrom("0");
        paging.setPagesize("50");
        listingRequest.setPaging(paging);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(listingRequest);
        return jsonPayload;
    }
}
