package com.foofinc.cfbra.json.jsondatastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//POJO to map game from weekly games JSON

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fixture {
    Team[] teams;

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return teams[0].getSchool() + " " + teams[0].getPoints() +
                " - " + teams[1].getSchool() + " " + teams[1].getPoints();
    }
}
