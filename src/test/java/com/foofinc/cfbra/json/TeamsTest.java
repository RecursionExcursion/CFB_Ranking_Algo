package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.StatisticizedTeam;
import com.foofinc.cfbra.entity.Teams;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamsTest {


    @Test
    void getInstance() {
        Teams testTeams = Teams.getInstance();
        Teams newTestTeams = Teams.getInstance();
        assertEquals(testTeams, newTestTeams);
    }

    @Test
    void getCompleteTeams() {
        Teams testTeams = Teams.getInstance();

        StatisticizedTeam testCT = new StatisticizedTeam("Iowa");
        testTeams.getCompleteTeams().add(testCT);

        for(StatisticizedTeam ct :Teams.getInstance().getCompleteTeams()) assertEquals(ct, testCT);
    }
}