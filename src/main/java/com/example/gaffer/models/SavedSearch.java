package com.example.gaffer.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SavedSearch {

    @Id
    private Long id;

    @Column
    private ListingDTO filters;

    @Column
    private Set<Long> userIds;

    public SavedSearch() {
    }

    public SavedSearch(Long id, ListingDTO filters, Set<Long> userIds) {
        this.id = id;
        this.filters = filters;
        this.userIds = userIds;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListingDTO getFilters() {
        return this.filters;
    }

    public void setFilters(ListingDTO filters) {
        this.filters = filters;
    }

    public Set<Long> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }
}
