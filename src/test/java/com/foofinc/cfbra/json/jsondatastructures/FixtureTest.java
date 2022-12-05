package com.foofinc.cfbra.json.jsondatastructures;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixtureTest {

    private final Fixture testFix;
    private final Team[] teamArr;

    public FixtureTest() {
        teamArr = new Team[]{new Team(), new Team()};
        this.testFix = new Fixture(teamArr);
    }

    @Test
    void getTeams() {
        assertEquals(testFix.getTeams(), teamArr);
    }

    @Test
    void setTeams() {
        Team[] newTeamsArr = {new Team(), new Team()};
        testFix.setTeams(newTeamsArr);
        assertEquals(testFix.getTeams(), newTeamsArr);
    }
}