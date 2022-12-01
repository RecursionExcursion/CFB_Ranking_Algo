package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.CompleteTeam;
import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;
import com.foofinc.cfbra.json.jsondatastructures.Stats;
import com.foofinc.cfbra.json.jsondatastructures.Team;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompleteTeamMapperTest {

    private CompleteTeamMapper mapper;

    public CompleteTeamMapperTest() {
        Map<School, List<Fixture>> testMap =new HashMap<>();

        Team[] teams = {new Team("Oregon",28,new Stats[]{new Stats("totalYards","400")}),
                new Team("Oregon State",21,new Stats[]{new Stats("totalYards","300")})};

        Fixture testFixture = new Fixture(teams);

        List<Fixture> fixtures = new ArrayList<>();
        fixtures.add(testFixture);

        testMap.put(new School("Oregon","Ducks", "UO"), fixtures);

        for(Map.Entry<School,List<Fixture>> e : testMap.entrySet()){
            this.mapper = new CompleteTeamMapper(e);
            break;
        }
    }

    @Test
    void getCompleteTeam() {
        CompleteTeam completeTeamTest = mapper.getCompleteTeam();

        assertEquals(completeTeamTest.getName(),"Oregon");
        assertEquals(completeTeamTest.getWins(),1);
        assertEquals(completeTeamTest.getPointsFor(),28);
        assertEquals(completeTeamTest.getPointsAllowed(),21);
        assertEquals(completeTeamTest.getTotalOffense(),400);
        assertEquals(completeTeamTest.getTotalDefense(),300);
    }
}