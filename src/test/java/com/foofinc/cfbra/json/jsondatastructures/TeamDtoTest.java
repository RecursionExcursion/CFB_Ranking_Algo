package com.foofinc.cfbra.json.jsondatastructures;

import com.foofinc.cfbra.api.dto.StatsDto;
import com.foofinc.cfbra.api.dto.TeamDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamDtoTest {

    private final TeamDto testTeam;
    private final StatsDto[] fooStatsArr;

    public TeamDtoTest() {
        fooStatsArr = new StatsDto[]{new StatsDto("Games won", "12")};
        testTeam = new TeamDto("Alabama", 21, fooStatsArr);
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
        StatsDto[] barStatsArr = new StatsDto[]{new StatsDto("Games Lost", "0")};
        testTeam.setStats(barStatsArr);
        assertEquals(testTeam.getStats(),barStatsArr);
    }
}