package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.StatisticizedTeam;
import com.foofinc.cfbra.entity.CompleteTeamMapper;
import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;
import com.foofinc.cfbra.api.jsondatastructures.Stats;
import com.foofinc.cfbra.api.jsondatastructures.Team;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticizedTeamMapperTest {

    private CompleteTeamMapper mapper;

    public StatisticizedTeamMapperTest() {
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
        StatisticizedTeam statisticizedTeamTest = mapper.getCompleteTeam();

        assertEquals(statisticizedTeamTest.getName(), "Oregon");
        assertEquals(statisticizedTeamTest.getWins(), 1);
        assertEquals(statisticizedTeamTest.getPointsFor(), 28);
        assertEquals(statisticizedTeamTest.getPointsAllowed(), 21);
        assertEquals(statisticizedTeamTest.getTotalOffense(), 400);
        assertEquals(statisticizedTeamTest.getTotalDefense(), 300);
    }
}