package com.foofinc.cfbra.entity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Singleton wrapper class of List<School>
 */

public enum SchoolList {

    INSTANCE;

    //    private List<School> schools = new ArrayList<>();
    private Schools schools = new Schools(new ArrayList<>());

    public void loadSchools(Schools s) {
        schools.loadSchools(s);
    }

    public void loadSchools(List<School> s) {
        schools.loadSchools(s);
    }

    public List<School> getSchoolsAsList() {
        return schools.getSchoolsAsList();
    }

    public Schools getSchoolsAsSchools() {
        return schools;
    }

    public void addSchool(School school) {
        schools.addSchool(school);
    }

    public Optional<School> getSchoolFromString(String schoolString) {
        return schools.getSchoolsAsList()
                      .stream()
                      .filter(s -> s.getSchoolNameString()
                                    .equals(schoolString))
                      .findFirst();
    }
}
