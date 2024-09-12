package com.example.gaffer.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

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

        System.out.println("Locations have been imported successfully.");

        // RestTemplate restTemplate = new RestTemplate();
        // System.out.println("Request reveived");

        // String url = "https://gateway.daft.ie/old/v1/listings";
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15");
        // headers.add("brand", "daft");
        // headers.add("platform", "web");

        // boolean stop=false;
        // int page=0;
        // while (stop==false){    
        //     HttpEntity<String> entity = new HttpEntity<>("{\"section\": \"residential-to-rent\",\"paging\": { \"from\": \""
        //      + String.valueOf(page) + "\", \"pagesize\": \"50\" }}}", headers);
        //     ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        //     if(response.getBody().contains("\"displayingFrom\":0,\"displayingTo\":0") || response.getBody()==null){
        //         stop=true;
        //         break;
        //     }
        //     try {
        //         JSONObject obj = new JSONObject(response.getBody());
        //         JSONArray jsonlisting = obj.getJSONArray("listings");
        //         for (int i=0; i<jsonlisting.length(); i++) {
        //             JSONObject listingObject = jsonlisting.getJSONObject(i).getJSONObject("listing");

        //             String id = String.valueOf(listingObject.getInt("id"));
        //             String title = listingObject.optString("title");
        //             String seoTitle = listingObject.getString("seoTitle");

        //             JSONArray sectionsArray = listingObject.getJSONArray("sections");
        //             List<String> sections = new ArrayList<>();
        //             for (int j = 0; j < sectionsArray.length(); j++) {
        //                 sections.add(sectionsArray.getString(j));
        //             }

        //             JSONArray saleTypeArray = listingObject.getJSONArray("saleType");
        //             List<String> saleType = new ArrayList<>();
        //             for (int j = 0; j < saleTypeArray.length(); j++) {
        //                 saleType.add(saleTypeArray.getString(j));
        //             }

        //             long publishDateMillis = listingObject.getLong("publishDate");
        //             LocalDateTime publishDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(publishDateMillis), ZoneId.systemDefault());

        //             String price = listingObject.getString("price");
        //             String abbreviatedPrice = listingObject.optString("abbreviatedPrice");
        //             String numBedrooms = listingObject.optString("numBedrooms");
        //             String numBathrooms = listingObject.optString("numBathrooms");
        //             String propertyType = listingObject.optString("propertyType");

        //             List<String> images = new ArrayList<>();
        //             JSONObject mediaObject = listingObject.getJSONObject("media");
        //             if (mediaObject.has("images")) {
        //                 JSONArray imagesArray = listingObject.getJSONObject("media").getJSONArray("images");
        //                 for (int j = 0; j < imagesArray.length(); j++) {
        //                     images.add(imagesArray.getJSONObject(j).optString("size72x52", ""));
        //                 }
        //             }

        //             String xPoint = listingObject.getJSONObject("point").optString("xPoint", "");
        //             String yPoint = listingObject.getJSONObject("point").optString("yPoint", "");
        //             String seoFriendlyPath = listingObject.optString("seoFriendlyPath");
        //             String category = listingObject.optString("category");
        //             String state = listingObject.optString("state");
        //             Listing listing = new Listing(id, title, seoTitle, sections, saleType, publishDate, price,
        //                     abbreviatedPrice, numBedrooms, numBathrooms, propertyType, images, xPoint, yPoint,
        //                     seoFriendlyPath, category, state);

        //             listingRepository.save(listing);
        //             page++;
        //         }
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
        // System.out.println("Listings have been imported successfully.");

    }

}

