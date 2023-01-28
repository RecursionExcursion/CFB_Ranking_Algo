package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.StatisticizedTeam;
import com.foofinc.cfbra.entity.Teams;
import com.foofinc.cfbra.entity.model.School;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamsTest {


    @Test
    void getInstance() {
        Teams testTeams = Teams.INSTANCE;
        Teams newTestTeams = Teams.INSTANCE;
        assertEquals(testTeams, newTestTeams);
    }

    @Test
    void getCompleteTeams() {
        Teams testTeams = Teams.INSTANCE;

        StatisticizedTeam testCT = new StatisticizedTeam(new School("Iowa", "Hawkeyes", "UI"));
        testTeams.getCompleteTeams().add(testCT);

        for(StatisticizedTeam ct :Teams.INSTANCE.getCompleteTeams()) assertEquals(ct, testCT);
    }
}