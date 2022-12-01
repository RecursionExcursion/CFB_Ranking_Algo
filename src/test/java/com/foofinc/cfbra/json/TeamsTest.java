package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.CompleteTeam;
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

        CompleteTeam testCT = new CompleteTeam("Iowa");
        testTeams.getCompleteTeams().add(testCT);

        for(CompleteTeam ct :Teams.getInstance().getCompleteTeams()) assertEquals(ct,testCT);
    }
}