package com.example.gaffer.models;

import java.util.List;

public class GeoFilter {
    private List<String> storedShapeIds;
    private String geoSearchType;

    public List<String> getStoredShapeIds() {
        return this.storedShapeIds;
    }

    public void setStoredShapeIds(List<String> storedShapeIds) {
        this.storedShapeIds = storedShapeIds;
    }

    public String getGeoSearchType() {
        return this.geoSearchType;
    }

    public void setGeoSearchType(String geoSearchType) {
        this.geoSearchType = geoSearchType;
    }
}
