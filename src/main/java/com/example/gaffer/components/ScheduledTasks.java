package com.example.gaffer.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.gaffer.models.AutoServiceDTO;
import com.example.gaffer.models.Listing;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.AutoRentService;

@Component
public class ScheduledTasks {
    
    private final UserEntityRepository userRepository;
    private final AutoRentService autoRentService;

    public ScheduledTasks(UserEntityRepository userRepository, AutoRentService autoRentService) {
        this.userRepository = userRepository;
        this.autoRentService = autoRentService;
    }

    // @Scheduled(cron = "0 0 * * * *")
    // public void dispatchAutoApplications() throws ClientProtocolException, IOException, ParseException{
    //     List<UserEntity> users = userRepository.findByAutoEnabledTrue();
    //     Map<String, List<UserEntity>> map = new HashMap<>();

    //     for (UserEntity user : users) {
    //         for (AutoServiceDTO auto : user.getAutoservices()) {
    //             String jsonListing = autoRentService.createJsonListing(auto);
    //             map.computeIfAbsent(jsonListing, k -> new ArrayList<>()).add(user);
    //         }
    //     }

    //     PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    //     connManager.setMaxTotal(50);
    //     connManager.setDefaultMaxPerRoute(10);

    //     CloseableHttpClient httpClient = HttpClients.custom()
    //             .setConnectionManager(connManager)
    //             .build();

    //     RestTemplate restTemplate = new RestTemplate();
    //     System.out.println("Request reveived");
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    //     headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15");
    //     headers.add("brand", "daft");
    //     headers.add("platform", "web");

    //     for (Entry<String, List<UserEntity>> pair : map.entrySet()) {
    //         HttpEntity<String> entity = new HttpEntity<>(pair.getKey(), headers);
    //         String url = "https://gateway.daft.ie/old/v1/listings";
    //         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    //         List<Listing> result = autoRentService.generateListings(response);
            
    //         HttpPost request = new HttpPost("https://gateway.daft.ie/old/v1/reply");
    //         StringEntity params = new StringEntity(pair.getKey());
    //         request.setEntity(params);
    //         request.setHeader("Host", "gateway.daft.ie");
    //         request.setHeader("Content-type", "application/json");
    //         request.setHeader("Connection", "keep-alive");
    //         request.setHeader("platform", "iOS");
    //         request.setHeader("Accept", "application/json");
    //         request.setHeader("brand", "daft");
    //         request.setHeader("Accept-Language", "en-GB,en;q=0.9");
    //         request.setHeader("Accept-Encoding", "gzip, deflate, br");
    //         request.setHeader("User-Agent", "Daft.ie/0 CFNetwork/1335.0.3 Darwin/21.6.0");

    //         for(Listing listing : result){
    //             for(UserEntity userEntity : pair.getValue()){
    //                 if(userEntity.getApplied()==null){
    //                     userEntity.setApplied(new HashSet<String>());
    //                 }
    //                 if(userEntity.getApplied().contains(listing.getId())){
    //                     break;
    //                 }
    //                 HttpPost craftedRequest = autoRentService.createApacheApplication(listing, userEntity);

    //                 CloseableHttpResponse emailResponse = httpClient.execute(craftedRequest);
    //                 userEntity.getApplied().add(listing.getId());
    //                 System.out.println("Response Code : " + emailResponse.getStatusLine().getStatusCode());
    //             }

    //         }

    //     }

    //     httpClient.close();
    // }
}
