package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.SchoolDto;

import java.io.Serializable;
import java.util.*;

public class SchoolAndFixturesDS implements Serializable {

    private final Map<SchoolDto, List<FixtureDto>> schoolMap;

    public SchoolAndFixturesDS() {
        this.schoolMap = new HashMap<>();
    }

    public SchoolAndFixturesDS(Map<SchoolDto, List<FixtureDto>> schoolMap) {
        this.schoolMap = schoolMap;
    }

    public List<FixtureDto> get(Object key) {
        return schoolMap.get(key);
    }

    public void putIfAbsent(SchoolDto key, List<FixtureDto> value) {
        schoolMap.putIfAbsent(key, value);
    }

    public Set<SchoolDto> keySet() {
        return schoolMap.keySet();
    }

    public Set<Map.Entry<SchoolDto, List<FixtureDto>>> entrySet() {
        return schoolMap.entrySet();
    }
}
