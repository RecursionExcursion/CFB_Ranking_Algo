package com.foofinc.cfbra.util;

import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.SchoolDto;
import com.foofinc.cfbra.api.dto.StatsDto;
import com.foofinc.cfbra.api.dto.TeamDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {

    public static String FILEPATH_READ = "src/test/java/com/foofinc/cfbra/persistence/test_data_read";
    public static String FILEPATH_WRITE = "src/test/java/com/foofinc/cfbra/persistence/test_data_write";
    public static String FILEPATH_TEAMS = "src/test/java/com/foofinc/cfbra/persistence/test_data_teams";


    public static Map<SchoolDto, List<FixtureDto>> createTestMap() {
        Map<SchoolDto, List<FixtureDto>> testMap = new HashMap<>();
        List<FixtureDto> fixtures = new ArrayList<>();


        TeamDto[] teams = {new TeamDto("Oregon", 28, new StatsDto[]{new StatsDto("totalYards", "400")}),
                new TeamDto("Oregon State", 21, new StatsDto[]{new StatsDto("totalYards", "300")})};

        TeamDto[] teams2 = {new TeamDto("Oregon", 21, new StatsDto[]{new StatsDto("totalYards", "300")}),
                new TeamDto("Washington State", 31, new StatsDto[]{new StatsDto("totalYards", "450")})};

        FixtureDto testFixture = new FixtureDto(teams);
        FixtureDto testFixture2 = new FixtureDto(teams2);

        fixtures.add(testFixture);
        fixtures.add(testFixture2);

        testMap.put(new SchoolDto("Oregon", "Ducks", "UO"), fixtures);

        return testMap;
    }

    public static Map<SchoolDto, List<FixtureDto>> createTestMap_OneFixture() {
        Map<SchoolDto, List<FixtureDto>> testMap = new HashMap<>();

        TeamDto[] teams = {new TeamDto("Oregon", 28, new StatsDto[]{new StatsDto("totalYards", "400")}),
                new TeamDto("Oregon State", 21, new StatsDto[]{new StatsDto("totalYards", "300")})};

        FixtureDto testFixture = new FixtureDto(teams);

        List<FixtureDto> fixtures = new ArrayList<>();
        fixtures.add(testFixture);

        testMap.put(new SchoolDto("Oregon", "Ducks", "UO"), fixtures);

        return testMap;
    }
}
