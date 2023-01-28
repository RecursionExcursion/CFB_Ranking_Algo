package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.entity.model.School;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Singleton wrapper class of List<School>
 */

public enum SchoolList {

    INSTANCE;

    private List<School> schools = new ArrayList<>();

    public void loadSchools(List<School> s) {
        schools = s;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void addSchool(School school) {
        schools.add(school);
    }

    public Optional<School> getSchoolFromString(String schoolString) {
        return schools.stream()
                      .filter(s -> s.getSchoolNameString()
                                    .equals(schoolString))
                      .findFirst();
    }
}
