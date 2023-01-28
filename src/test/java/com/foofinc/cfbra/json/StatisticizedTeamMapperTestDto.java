package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.SchoolToStatTeamMapper;
import com.foofinc.cfbra.entity.StatisticizedTeam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticizedTeamMapperTestDto {

    private SchoolToStatTeamMapper mapper;

    public StatisticizedTeamMapperTestDto() {

    }

    @Test
    void getCompleteTeam() {
        StatisticizedTeam statisticizedTeamTest = mapper.getCompleteTeam();

        assertEquals(statisticizedTeamTest.getSchool(), "Oregon");
        assertEquals(statisticizedTeamTest.getWins(), 1);
        assertEquals(statisticizedTeamTest.getPointsFor(), 28);
        assertEquals(statisticizedTeamTest.getPointsAllowed(), 21);
        assertEquals(statisticizedTeamTest.getTotalOffense(), 400);
        assertEquals(statisticizedTeamTest.getTotalDefense(), 300);
    }
}