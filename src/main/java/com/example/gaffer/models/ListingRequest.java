package com.example.gaffer.models;

import java.util.List;

public class ListingRequest {
    
    private String section;
    private List<Filter> filters;
    private GeoFilter geoFilter;
    private List<Range> ranges;
    private Paging paging;

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<Filter> getFilters() {
        return this.filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public GeoFilter getGeoFilter() {
        return this.geoFilter;
    }

    public void setGeoFilter(GeoFilter geoFilter) {
        this.geoFilter = geoFilter;
    }

    public List<Range> getRanges() {
        return this.ranges;
    }

    public void setRanges(List<Range> ranges) {
        this.ranges = ranges;
    }

    public Paging getPaging() {
        return this.paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
