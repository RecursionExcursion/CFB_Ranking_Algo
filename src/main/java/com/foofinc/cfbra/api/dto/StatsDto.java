package com.foofinc.cfbra.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map stats from a game (fixture) JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties()
public class StatsDto implements DTO {
    private String category;
    private String stat;

    public StatsDto() {
    }

    public StatsDto(String category, String stat) {
        this.category = category;
        this.stat = stat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "category='" + category + '\'' +
                ", stat='" + stat + '\'' +
                '}';
    }
}

