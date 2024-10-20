package com.example.gaffer.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.gaffer.models.Listing;
import com.example.gaffer.models.LocationEntity;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.repositories.LocationRepository;

@Component
public class DataImporter implements CommandLineRunner {

    private final LocationRepository locationEntityRepository;
    private final ListingRepository listingRepository;

    public DataImporter(LocationRepository locationEntityRepository, ListingRepository listingRepository) {
        this.locationEntityRepository = locationEntityRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("locations.txt");

        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            lines = reader.lines().collect(Collectors.toList());
        }

        for (String line : lines) {
            String[] splitLines = line.split(",");
            LocationEntity location = new LocationEntity();
            location.setBigName(splitLines[0].replace(",", "").trim());
            location.setCode(splitLines[1].replace(",", "").trim());
            location.setName(splitLines[2].replace(",", "").trim());
            location.setSlug(splitLines[3].replace(",", "").trim());
            locationEntityRepository.save(location);
        }

        List<LocationEntity> allOfThem = locationEntityRepository.findAll();
        Collections.sort(allOfThem, (a, b) -> Integer.valueOf(a.getCode()) - Integer.valueOf(b.getCode()));
        // for(LocationEntity val : allOfThem){
        //     System.out.println(val.toString());
        // }
        System.out.println("Locations have been imported successfully.");

        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Request reveived");

        String url = "https://gateway.daft.ie/old/v1/listings";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15");
        headers.add("brand", "daft");
        headers.add("platform", "web");

        boolean stop=false;
        int page=0;
        while (stop==false){    
            HttpEntity<String> entity = new HttpEntity<>(
            "{\"section\": \"residential-to-rent\"," +
            "\"geoFilter\": { \"storedShapeIds\": [\"1\"], \"geoSearchType\": \"STORED_SHAPES\" }," +
            "\"paging\": { \"from\": \"" + String.valueOf(page) + "\", \"pagesize\": \"50\" } }",
            headers
        );

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if(response.getBody().contains("\"displayingFrom\":0,\"displayingTo\":0") || response.getBody()==null){
                stop=true;
                break;
            }
            try {
                JSONObject obj = new JSONObject(response.getBody());
                JSONArray jsonlisting = obj.getJSONArray("listings");
                for (int i=0; i<jsonlisting.length(); i++) {
                    JSONObject listingObject = jsonlisting.getJSONObject(i).getJSONObject("listing");

                    // String id = String.valueOf(listingObject.getInt("id"));
                    String title = listingObject.optString("title");
                    String seoTitle = listingObject.getString("seoTitle");

                    JSONArray sectionsArray = listingObject.getJSONArray("sections");
                    List<String> sections = new ArrayList<>();
                    for (int j = 0; j < sectionsArray.length(); j++) {
                        sections.add(sectionsArray.getString(j));
                    }

                    JSONArray saleTypeArray = listingObject.getJSONArray("saleType");
                    List<String> saleType = new ArrayList<>();
                    for (int j = 0; j < saleTypeArray.length(); j++) {
                        saleType.add(saleTypeArray.getString(j));
                    }

                    long publishDateMillis = listingObject.getLong("publishDate");
                    LocalDateTime publishDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(publishDateMillis), ZoneId.systemDefault());

                    String price = listingObject.getString("price");
                    price = price.replaceAll("[^\\d]", "");
                    String abbreviatedPrice = listingObject.optString("abbreviatedPrice");
                    // String numBedrooms = listingObject.optString("numBedrooms");
                    // java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\d+").matcher(numBedrooms);
                    // if (matcher.find()) {
                    //     numBedrooms = matcher.group();
                    // }
                    // String numBathrooms = listingObject.optString("numBathrooms");
                    String propertyType = listingObject.optString("propertyType");
                    if(propertyType.equals("Apartment")) propertyType = "apartment";
                    if(propertyType.equals("House")) propertyType = "house";
                    if(propertyType.equals("Studio")) propertyType = "studio";

                    List<String> images = new ArrayList<>();
                    JSONObject mediaObject = listingObject.getJSONObject("media");
                    if (mediaObject.has("images")) {
                        JSONArray imagesArray = listingObject.getJSONObject("media").getJSONArray("images");
                        for (int j = 0; j < imagesArray.length(); j++) {
                            images.add(imagesArray.getJSONObject(j).optString("size720x480", ""));
                        }
                    }

                    String xPoint = listingObject.getJSONObject("point").optString("xPoint", "");
                    String yPoint = listingObject.getJSONObject("point").optString("yPoint", "");
                    String seoFriendlyPath = listingObject.optString("seoFriendlyPath");
                    String category = listingObject.optString("category");
                    String state = listingObject.optString("state");
                    // Listing listing = new Listing(String.valueOf(i), title, seoTitle, sections, saleType, publishDate, Integer.valueOf(price),
                    //         abbreviatedPrice, numBedrooms, numBathrooms, propertyType, images, xPoint, yPoint,
                            // seoFriendlyPath, category, state);
                    Listing listing = new Listing(String.valueOf(i+1), title, "Dublin County", sections, saleType, publishDate, Integer.valueOf(price), 
                            abbreviatedPrice, 1, 1, propertyType, 
                            images, xPoint, yPoint, seoFriendlyPath, category, state);
                    
                    listing.setUserId("1");
                    listing.setApplications(new HashSet<String>());

                    listingRepository.save(listing);
                    page++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Listings have been imported successfully.");

        // for(LocationEntity val : allOfThem){
        //     System.out.println(val.toString());
        // }

    }

}

