package com.foofinc.cfbra.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CfbApiAccess {

    private final String schoolsUrlString = "https://api.collegefootballdata.com/teams/fbs?year=2022";

    private final String teamGamesUrlString = "https://api.collegefootballdata" +
            ".com/games/teams?year=2022&seasonType=regular";

    private final List<School> schools;
    private final List<Fixture[]> weeks;


    public CfbApiAccess() {
        schools = getAllSchools();
        weeks = getFixturesForAllWeeks();
    }

    public List<Fixture[]> getWeeks() {
        return List.copyOf(weeks);
    }

    public List<School> getSchools() {
        return List.copyOf(schools);
    }

    private List<School> getAllSchools() {
        return sendGetRequestForAllAFBSSchools();
    }

    private List<Fixture[]> getFixturesForAllWeeks() {
        List<Fixture[]> tempFixes = new ArrayList<>();
        int startingWeek = 1, weeksInSeason = 13;
        for (int i = startingWeek; i <= weeksInSeason; i++) {
            tempFixes.add(sendGetRequestForWeekFixtures(i));
        }
        return tempFixes;
    }

    private List<School> sendGetRequestForAllAFBSSchools() {
        try {
            return mapSchoolsFromJSON(getJSON(schoolsUrlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Fixture[] sendGetRequestForWeekFixtures(int week) {
        try {
            return mapWeekFromJSON(getJSON(teamGamesUrlString + "&week=" + week));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private List<School> mapSchoolsFromJSON(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<>() {
        });
    }

    private Fixture[] mapWeekFromJSON(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Fixture[].class);
    }

    private String getJSON(String s) throws IOException {
        APICaller apiCaller = new APICaller();
        return apiCaller.getJSONFromAPICall(s,"gLQdG5n7YtiTjzu/bxxxd+rdzzrhWftHTtIH7PAGVWlAQMOAA7h2ria3ai2Dl9zc");
    }
}
