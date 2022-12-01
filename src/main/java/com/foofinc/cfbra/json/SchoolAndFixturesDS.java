package com.foofinc.cfbra.json;

import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;

import java.util.*;

public class SchoolAndFixturesDS implements Map<School, List<Fixture>> {

    private final Map<School, List<Fixture>> schoolMap;


    public SchoolAndFixturesDS() {
        this.schoolMap = new HashMap<>();
    }

    public SchoolAndFixturesDS(Map<School, List<Fixture>> schoolMap) {
        this.schoolMap = schoolMap;
    }

    public void addEntry(Map.Entry<School, List<Fixture>> entry) {
        schoolMap.put(entry.getKey(), entry.getValue());
    }

    public void addEntryIfAbsent(Map.Entry<School, List<Fixture>> entry) {
        schoolMap.putIfAbsent(entry.getKey(), entry.getValue());
    }

    public Map<School, List<Fixture>> getSchoolMap() {
        return schoolMap;
    }

    @Override
    public int size() {
        return keySet().size();
    }

    @Override
    public boolean isEmpty() {
        return keySet().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return schoolMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return schoolMap.containsValue(value);
    }

    @Override
    public List<Fixture> get(Object key) {
        return schoolMap.get(key);
    }

    @Override
    public List<Fixture> put(School key, List<Fixture> value) {
        return schoolMap.put(key, value);
    }

    @Override
    public List<Fixture> putIfAbsent(School key, List<Fixture> value) {
        return schoolMap.putIfAbsent(key, value);
    }

    @Override
    public List<Fixture> remove(Object key) {
        return schoolMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends School, ? extends List<Fixture>> m) {

    }

    @Override
    public void clear() {
        schoolMap.clear();
    }

    @Override
    public Set<School> keySet() {
        return schoolMap.keySet();
    }

    @Override
    public Collection<List<Fixture>> values() {
        return schoolMap.values();
    }

    @Override
    public Set<Entry<School, List<Fixture>>> entrySet() {
        return schoolMap.entrySet();
    }
}
