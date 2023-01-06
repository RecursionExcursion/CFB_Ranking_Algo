package com.foofinc.cfbra.api.jsondatastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Arrays;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map team from weekly games JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team implements Serializable {
    private String school;
    private int points;
    private Stats[] stats;

    public Team() {
    }

    public Team(String school, int points, Stats[] stats) {
        this.school = school;
        this.points = points;
        this.stats = stats;
    }

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

    @Override
    public String toString() {
        return "Team{" +
                "school='" + school + '\'' +
                ", points=" + points +
                ", stats=" + Arrays.toString(stats) +
                '}';
    }
}
