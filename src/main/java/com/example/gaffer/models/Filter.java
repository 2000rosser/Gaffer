package com.example.gaffer.models;

import java.util.List;

public class Filter {
    private String name;
    private List<String> values;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
