package com.example.gaffer.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.gaffer.models.Listing;
import com.example.gaffer.models.ListingDTO;
import com.example.gaffer.models.SavedSearch;
import com.example.gaffer.models.UserDto;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.ListingRepository;
import com.example.gaffer.repositories.SavedSearchRepository;
import com.example.gaffer.repositories.UserEntityRepository;
import com.example.gaffer.services.AutoRentService;
import com.example.gaffer.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;



@Controller
@SessionAttributes("listings")
public class SearchListings {
    
    private final AutoRentService autoService;
    private final ListingRepository listingRepository;
    private final UserEntityRepository userRepository;
    private final SavedSearchRepository savedSearchRepository;
    private final UserService userService;

    @Value("${google.maps.api.key}")
    private String apiKey;

    public SearchListings(AutoRentService autoService, ListingRepository listingRepository, UserEntityRepository userRepository, SavedSearchRepository savedSearchRepository, UserService userService) {
        this.autoService = autoService;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.savedSearchRepository = savedSearchRepository;
        this.userService = userService;
    }


    @GetMapping("/listings")
    public String getAutoRent(Model model){
        model.addAttribute("listingDto", new ListingDTO());
        return "listing-search";
    }

    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("listingDto", new ListingDTO());
        return "home";
    }

    @GetMapping("/")
    public String getRoot(Model model){
        model.addAttribute("listingDto", new ListingDTO());
        return "home";
    }

    @GetMapping("/autocomplete-auto")
    @ResponseBody
    public List<String> getLocations(@RequestParam("term") String term) {
        Pageable limit = PageRequest.of(0, 5);
        List<String> result = autoService.getBigName(term.toLowerCase(), limit);
        return result;
    }

    @PostMapping("/search")
    public String fetchListings(@ModelAttribute ListingDTO listingDto, Model model){
        if(listingDto.getFurnishing().equals("Any") || listingDto.getFurnishing().equals("")) listingDto.setFurnishing(null);
        if(listingDto.getPropertyType().equals("Any") || listingDto.getPropertyType().equals("")) listingDto.setPropertyType(null);
        List<Listing> listings = listingRepository.searchListings(listingDto.getLocation().isEmpty() || listingDto.getLocation() == null ? null : listingDto.getLocation(), Integer.valueOf(listingDto.getMinPrice()), Integer.valueOf(listingDto.getMaxPrice()), 
        Integer.valueOf(listingDto.getMinBeds()), Integer.valueOf(listingDto.getMaxBeds()), listingDto.getPropertyType(), listingDto.getFurnishing());
        model.addAttribute("listings", listings);
        model.addAttribute("listingDto", listingDto);
        
        return "listing-search";
    }

    /*
     * Saved search database. ID is the hashcode of the search filter,
     * Have a set of of user ID's that use the filter.
     * When new notification posted, should loop through each filter and send notification to each user
     * 
     * OR
     * do we also store the filters in the SearchFilter
     * could then queery matching filters when new listing posted
     */
    @PostMapping("/save-search")
    public String saveSearch(@ModelAttribute ListingDTO listingDto, Model model, Authentication authentication) {
        Optional<UserEntity> optionalUser = userRepository.findById(((UserEntity) authentication.getPrincipal()).getId());
        UserEntity user = new UserEntity();
        if(optionalUser.isPresent()) user = optionalUser.get();
        else return "/login";

        if(listingDto.getFurnishing().equals("any") || listingDto.getFurnishing().equals("")) listingDto.setFurnishing(null);
        if(listingDto.getPropertyType().equals("any") || listingDto.getPropertyType().equals("")) listingDto.setPropertyType(null);

        Long hash = Long.valueOf(listingDto.hashCode());

        Optional<SavedSearch> search = savedSearchRepository.findById(hash);

        Set<Long> userSavedSearches = new HashSet<>();
        if(search.isPresent()){
            Set<Long> filterSubscribers = search.get().getUserIds();
            filterSubscribers.add(user.getId());
            savedSearchRepository.save(search.get());
            if(user.getSavedSearches()==null){
                userSavedSearches.add(hash);
            }else{
                userSavedSearches = user.getSavedSearches();
                userSavedSearches.add(hash);
            }
            
        }else{
            Set<Long> filterSubscribers = new HashSet<>();
            filterSubscribers.add(user.getId());
            SavedSearch searchNew = new SavedSearch(Long.valueOf(listingDto.hashCode()), listingDto, filterSubscribers);
            savedSearchRepository.save(searchNew);
            if(user.getSavedSearches()==null){
                userSavedSearches.add(hash);
            }else{
                userSavedSearches = user.getSavedSearches();
                userSavedSearches.add(hash);
            }
        }

        user.setSavedSearches(userSavedSearches);

        userRepository.save(user);

        model.addAttribute("listingDto", new ListingDTO());
        
        return "listing-search";
    }

    @GetMapping("/save-search")
    public String getSaveSearch(Model model, Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        
        Optional<UserEntity> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) {
            return "redirect:/login";
        }

        Set<Long> searchIds = optionalUser.get().getSavedSearches();
        List<ListingDTO> dtos = new ArrayList<>();
        if(searchIds!=null && searchIds.size()>0){
            List<SavedSearch> savedSearches = savedSearchRepository.findAllById(searchIds);

            dtos = savedSearches.stream()
                                                .map(SavedSearch::getFilters)
                                                .collect(Collectors.toList());
        }
        model.addAttribute("listingDtos", dtos);
        return "saved-searches";
    }


    /*
     * Landlords have a List<int> applications where each value in list is an application ID/user ID?
     * Links to user profiles displayed on landlord applications dashboard
     */
    @PostMapping("/apply")
    public ResponseEntity<String> applyToRent(@ModelAttribute Listing listing, Authentication authentication, Model model, @SessionAttribute("listings") List<Listing> listings) throws JsonProcessingException {
        if(authentication==null || !authentication.isAuthenticated()) return ResponseEntity.status(401).body("Unauthorised");
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Listing applyingTo = listingRepository.getReferenceById(listing.getId());
        Set<String> listingApps = applyingTo.getApplications();
        if(listingApps.contains(String.valueOf(user.getId()))) return ResponseEntity.status(409).body("Application already exists");
        listingApps.add(String.valueOf(user.getId()));
        applyingTo.setApplications(listingApps);
        listingRepository.save(applyingTo);
        UserEntity landlord = userRepository.findById(Long.valueOf(applyingTo.getUserId())).get();
        if(landlord.getApplications()==null) landlord.setApplications(new HashSet<>());
        Set<String> landlordApps = landlord.getApplications();
        landlordApps.add(listing.getId());
        user.setApplications(landlordApps);
        userRepository.save(user);
        
        return ResponseEntity.ok("Success");        
    }

    @GetMapping("/listing/{id}")
    public String viewListing(Model model, @PathVariable(required=true, name="id") String id, Authentication authentication){
        Listing listing = listingRepository.findById(id).get();
        UserDto userDto = userService.getUserProfile(Long.valueOf(listing.getUserId()));
        model.addAttribute("landlord", userDto);
        model.addAttribute("listing", listing);
        model.addAttribute("user", userService.getUserProfile(((UserEntity) authentication.getPrincipal()).getId()));
        return "listing";
    }

    @GetMapping("/map-url")
    @ResponseBody
    public String getMapUrl(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        String mapUrl = "https://www.google.com/maps/embed/v1/place?key=" + apiKey + "&q=" + lat + "," + lng;
        return mapUrl;
    }
}