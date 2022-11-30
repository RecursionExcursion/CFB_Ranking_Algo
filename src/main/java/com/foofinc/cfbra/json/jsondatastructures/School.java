package com.foofinc.cfbra.json.jsondatastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//POJO to map from all schools JSON

@JsonIgnoreProperties(ignoreUnknown = true)
public class School {
    private String school;
    private String mascot;
    private String abbreviation;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
