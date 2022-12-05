package com.foofinc.cfbra.util;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;
import com.foofinc.cfbra.api.jsondatastructures.Stats;
import com.foofinc.cfbra.api.jsondatastructures.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {

    public static String FILEPATH_READ = "src/test/java/com/foofinc/cfbra/persistence/test_data_read";
    public static String FILEPATH_WRITE = "src/test/java/com/foofinc/cfbra/persistence/test_data_write";
    public static String FILEPATH_TEAMS = "src/test/java/com/foofinc/cfbra/persistence/test_data_teams";


    public static Map<School, List<Fixture>> createTestMap() {
        Map<School, List<Fixture>> testMap = new HashMap<>();
        List<Fixture> fixtures = new ArrayList<>();


        Team[] teams = {new Team("Oregon", 28, new Stats[]{new Stats("totalYards", "400")}),
                new Team("Oregon State", 21, new Stats[]{new Stats("totalYards", "300")})};

        Team[] teams2 = {new Team("Oregon", 21, new Stats[]{new Stats("totalYards", "300")}),
                new Team("Washington State", 31, new Stats[]{new Stats("totalYards", "450")})};

        Fixture testFixture = new Fixture(teams);
        Fixture testFixture2 = new Fixture(teams2);

        fixtures.add(testFixture);
        fixtures.add(testFixture2);

        testMap.put(new School("Oregon", "Ducks", "UO"), fixtures);

        return testMap;
    }

    public static Map<School, List<Fixture>> createTestMap_OneFixture() {
        Map<School, List<Fixture>> testMap = new HashMap<>();

        Team[] teams = {new Team("Oregon", 28, new Stats[]{new Stats("totalYards", "400")}),
                new Team("Oregon State", 21, new Stats[]{new Stats("totalYards", "300")})};

        Fixture testFixture = new Fixture(teams);

        List<Fixture> fixtures = new ArrayList<>();
        fixtures.add(testFixture);

        testMap.put(new School("Oregon", "Ducks", "UO"), fixtures);

        return testMap;
    }
}
