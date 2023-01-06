package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;

import java.io.Serializable;
import java.util.*;

public class SchoolAndFixturesDS implements Serializable {

    private final Map<School, List<Fixture>> schoolMap;

    public SchoolAndFixturesDS() {
        this.schoolMap = new HashMap<>();
    }

    public SchoolAndFixturesDS(Map<School, List<Fixture>> schoolMap) {
        this.schoolMap = schoolMap;
    }

    public List<Fixture> get(Object key) {
        return schoolMap.get(key);
    }

    public void putIfAbsent(School key, List<Fixture> value) {
        schoolMap.putIfAbsent(key, value);
    }

    public Set<School> keySet() {
        return schoolMap.keySet();
    }

    public Set<Map.Entry<School, List<Fixture>>> entrySet() {
        return schoolMap.entrySet();
    }
}
