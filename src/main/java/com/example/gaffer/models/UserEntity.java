package com.example.gaffer.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "app_users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String location;

    @Column
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_references", joinColumns = @JoinColumn(name = "user_id"))
    private List<Reference> references;

    @Column
    private Set<String> applications;

    @Column
    private List<String> idDocument;

    @Column
    private List<String> workReference;

    @Column
    private List<String> landlordReference;

    @Column
    private String occupation;

    @Column
    private String placeOfWork;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean accountNonExpired;

    @Column(nullable = false)
    private boolean accountNonLocked;

    @Column(nullable = false)
    private boolean credentialsNonExpired;

    @Column
    private boolean landlord;

    @Column
    private boolean autoEnabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_autoservices", joinColumns = @JoinColumn(name = "user_id"))
    private List<AutoServiceDTO> autoservices;

    private Set<String> applied;

    @Column(name = "verification_code")
    private String verificationCode;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @Transient
    private MultipartFile[] idDoc;

    @Transient
    private MultipartFile[] workDoc;

    @Transient
    private MultipartFile[] landDoc;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", description='" + getDescription() + "'" +
            ", references='" + getReferences() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", placeOfWork='" + getPlaceOfWork() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", accountNonExpired='" + isAccountNonExpired() + "'" +
            ", accountNonLocked='" + isAccountNonLocked() + "'" +
            ", credentialsNonExpired='" + isCredentialsNonExpired() + "'" +
            ", autoEnabled='" + isAutoEnabled() + "'" +
            ", autoservices='" + getAutoservices() + "'" +
            ", verificationCode='" + getVerificationCode() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getAccountNonExpired() {
        return this.accountNonExpired;
    }


    public boolean getAccountNonLocked() {
        return this.accountNonLocked;
    }


    public boolean getCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }


    public String getVerificationCode() {
        return this.verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
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

    public List<Reference> getReferences() {
        return this.references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public Set<String> getApplications() {
        return this.applications;
    }

    public void setApplications(Set<String> applications) {
        this.applications = applications;
    }

    public List<String> getIdDocument() {
        return this.idDocument;
    }

    public void setIdDocument(List<String> idDocument) {
        this.idDocument = idDocument;
    }

    public List<String> getWorkReference() {
        return this.workReference;
    }

    public void setWorkReference(List<String> workReference) {
        this.workReference = workReference;
    }

    public List<String> getLandlordReference() {
        return this.landlordReference;
    }

    public void setLandlordReference(List<String> landlordReference) {
        this.landlordReference = landlordReference;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPlaceOfWork() {
        return this.placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean isLandlord() {
        return this.landlord;
    }

    public boolean getLandlord() {
        return this.landlord;
    }

    public void setLandlord(boolean landlord) {
        this.landlord = landlord;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isAutoEnabled() {
        return this.autoEnabled;
    }

    public boolean getAutoEnabled() {
        return this.autoEnabled;
    }

    public void setAutoEnabled(boolean autoEnabled) {
        this.autoEnabled = autoEnabled;
    }

    public List<AutoServiceDTO> getAutoservices() {
        return this.autoservices;
    }

    public void setAutoservices(List<AutoServiceDTO> autoservices) {
        this.autoservices = autoservices;
    }

    public Set<String> getApplied() {
        return this.applied;
    }

    public void setApplied(Set<String> applied) {
        if(this.applied==null){
            this.applied = new HashSet<String>();
        }
        this.applied = applied;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

}
