package com.foofinc.cfbra.api.jsondatastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/*
This class is a Data Structure used to hold data from Jackson's objectmapper object.
POJO to map game from weekly games JSON
No-Arg constructor and basic getter/setter methods for jackson mapping
Arg constructor for testing
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fixture {
    private Team[] teams;

    public Fixture(Team[] teams) {
        this.teams = teams;
    }

    public Fixture() {
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "Fixture{" +
                "teams=" + Arrays.toString(teams) +
                '}';
    }
}
