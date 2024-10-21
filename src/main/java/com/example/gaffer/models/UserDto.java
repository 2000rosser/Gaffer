package com.example.gaffer.models;

import org.springframework.web.multipart.MultipartFile;

public class UserDto {

    private Long id;

    private String name;

    private String phoneNumber;

    private String location;

    private String description;

    private String occupation;

    private String username;

    private String placeOfWork;

    private String profilePicture;

    private MultipartFile[] idDoc;

    private MultipartFile[] workDoc;

    private MultipartFile[] landDoc;

    public UserDto() {
    }

    public UserDto(Long id, String name, String phoneNumber, String location, String description, String occupation, String username, String placeOfWork, String profilePicture, MultipartFile[] idDoc, MultipartFile[] workDoc, MultipartFile[] landDoc) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.description = description;
        this.occupation = occupation;
        this.username = username;
        this.placeOfWork = placeOfWork;
        this.profilePicture = profilePicture;
        this.idDoc = idDoc;
        this.workDoc = workDoc;
        this.landDoc = landDoc;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPlaceOfWork() {
        return this.placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public MultipartFile[] getIdDoc() {
        return this.idDoc;
    }

    public void setIdDoc(MultipartFile[] idDoc) {
        this.idDoc = idDoc;
    }

    public MultipartFile[] getWorkDoc() {
        return this.workDoc;
    }

    public void setWorkDoc(MultipartFile[] workDoc) {
        this.workDoc = workDoc;
    }

    public MultipartFile[] getLandDoc() {
        return this.landDoc;
    }

    public void setLandDoc(MultipartFile[] landDoc) {
        this.landDoc = landDoc;
    }

    public UserDto id(Long id) {
        setId(id);
        return this;
    }

    public UserDto name(String name) {
        setName(name);
        return this;
    }

    public UserDto phoneNumber(String phoneNumber) {
        setPhoneNumber(phoneNumber);
        return this;
    }

    public UserDto location(String location) {
        setLocation(location);
        return this;
    }

    public UserDto description(String description) {
        setDescription(description);
        return this;
    }

    public UserDto occupation(String occupation) {
        setOccupation(occupation);
        return this;
    }

    public UserDto username(String username) {
        setUsername(username);
        return this;
    }

    public UserDto placeOfWork(String placeOfWork) {
        setPlaceOfWork(placeOfWork);
        return this;
    }

    public UserDto idDoc(MultipartFile[] idDoc) {
        setIdDoc(idDoc);
        return this;
    }

    public UserDto workDoc(MultipartFile[] workDoc) {
        setWorkDoc(workDoc);
        return this;
    }

    public UserDto landDoc(MultipartFile[] landDoc) {
        setLandDoc(landDoc);
        return this;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

}
