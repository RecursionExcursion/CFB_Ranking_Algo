package com.foofinc.cfbra.json.jsondatastructures;

import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.TeamDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixtureDtoTest {

    private final FixtureDto testFix;
    private final TeamDto[] teamArr;

    public FixtureDtoTest() {
        teamArr = new TeamDto[]{new TeamDto(), new TeamDto()};
        this.testFix = new FixtureDto(teamArr);
    }

    @Test
    void getTeams() {
        assertEquals(testFix.getTeams(), teamArr);
    }

    @Test
    void setTeams() {
        TeamDto[] newTeamsArr = {new TeamDto(), new TeamDto()};
        testFix.setTeams(newTeamsArr);
        assertEquals(testFix.getTeams(), newTeamsArr);
    }
}