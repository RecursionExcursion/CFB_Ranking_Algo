package com.foofinc.cfbra.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map team from weekly games JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto implements DTO{
    private String school;
    private int points;
    private StatsDto[] stats;

    public TeamDto() {
    }

    public TeamDto(String school, int points, StatsDto[] stats) {
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

    public StatsDto[] getStats() {
        return stats;
    }

    public void setStats(StatsDto[] stats) {
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
