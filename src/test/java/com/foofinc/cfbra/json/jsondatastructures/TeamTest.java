package com.foofinc.cfbra.json.jsondatastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private final Team testTeam;
    private final Stats[] fooStatsArr;

    public TeamTest() {
        fooStatsArr = new Stats[]{new Stats("Games won", "12")};
        testTeam = new Team("Alabama", 21, fooStatsArr);
    }

    @Test
    void getSchool() {
        assertEquals(testTeam.getSchool(), "Alabama");
    }

    @Test
    void setSchool() {
        testTeam.setSchool("Auburn");
        assertEquals(testTeam.getSchool(), "Auburn");
    }

    @Test
    void getPoints() {
        assertEquals(testTeam.getPoints(), 21);
    }

    @Test
    void setPoints() {
        testTeam.setPoints(0);
        assertEquals(testTeam.getPoints(), 0);
    }

    @Test
    void getStats() {
        assertEquals(testTeam.getStats(), fooStatsArr);
    }

    @Test
    void setStats() {
        Stats[] barStatsArr = new Stats[]{new Stats("Games Lost", "0")};
        testTeam.setStats(barStatsArr);
        assertEquals(testTeam.getStats(),barStatsArr);
    }
}