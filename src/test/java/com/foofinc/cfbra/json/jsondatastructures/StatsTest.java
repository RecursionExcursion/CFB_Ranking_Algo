package com.foofinc.cfbra.json.jsondatastructures;

import com.foofinc.cfbra.api.jsondatastructures.Stats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    private final Stats testStats;

    public StatsTest() {
        testStats = new Stats("Beers drank", "12 if my team loses");
    }

    @Test
    void getCategory() {
        assertEquals(testStats.getCategory(),"Beers drank");
    }

    @Test
    void setCategory() {
        testStats.setCategory("Coffees drank");
        assertEquals(testStats.getCategory(),"Coffees drank");
    }

    @Test
    void getStat() {
        assertEquals(testStats.getStat(),"12 if my team loses");
    }

    @Test
    void setStat() {
        testStats.setStat("12 if it's early enough");
        assertEquals(testStats.getStat(),"12 if it's early enough");
    }
}