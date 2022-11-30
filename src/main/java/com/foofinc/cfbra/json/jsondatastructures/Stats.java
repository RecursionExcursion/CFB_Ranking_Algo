package com.foofinc.cfbra.json.jsondatastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//POJO to map stats from a game (fixture) JSON


@JsonIgnoreProperties()
public class Stats {
    private String category;
    private String stat;

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
}

