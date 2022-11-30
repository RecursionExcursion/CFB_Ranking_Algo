package com.foofinc.cfbra.json.jsondatastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//POJO to map team from weekly games JSON


@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
    private String school;
    private int points;
    private Stats[] stats;


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Stats[] getStats() {
        return stats;
    }

    public void setStats(Stats[] stats) {
        this.stats = stats;
    }
}
