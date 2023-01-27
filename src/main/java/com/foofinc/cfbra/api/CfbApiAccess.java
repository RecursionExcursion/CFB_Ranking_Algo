package com.foofinc.cfbra.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.SchoolDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CfbApiAccess {

    private final String schoolsUrlString = "https://api.collegefootballdata.com/teams/fbs?year=2022";
    private final String teamGamesUrlString = "https://api.collegefootballdata.com/games/teams?year=2022&seasonType=regular";

    private final List<SchoolDto> schools;

    private final List<FixtureDto[]> weeks;
//    private final List<Game> weeks;

    public CfbApiAccess() {
        schools = getAllSchools();
        weeks = getFixturesForAllWeeks();
    }

    public List<FixtureDto[]> getWeeks() {
        return List.copyOf(weeks);
    }

    public List<SchoolDto> getSchools() {
        return List.copyOf(schools);
    }

    private List<SchoolDto> getAllSchools() {
        return sendGetRequestForAllFBSSchools();
    }

    private List<FixtureDto[]> getFixturesForAllWeeks() {
        List<FixtureDto[]> tempFixes = new ArrayList<>();
        int startingWeek = 1, weeksInSeason = 13;
        for (int i = startingWeek; i <= weeksInSeason; i++) {
            tempFixes.add(sendGetRequestForWeekFixtures(i));
        }
        return tempFixes;
    }

    private List<SchoolDto> sendGetRequestForAllFBSSchools() {
        try {
            return mapSchoolsFromJSON(getJSON(schoolsUrlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FixtureDto[] sendGetRequestForWeekFixtures(int week) {
        try {
            return mapWeekFromJSON(getJSON(teamGamesUrlString + "&week=" + week));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private List<SchoolDto> mapSchoolsFromJSON(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<>() {
        });
    }

    private FixtureDto[] mapWeekFromJSON(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, FixtureDto[].class);
    }

    private String getJSON(String s) throws IOException {
        return APICaller.INSTANCE.getJSONFromAPICall(s,"gLQdG5n7YtiTjzu/bxxxd" +
                "+rdzzrhWftHTtIH7PAGVWlAQMOAA7h2ria3ai2Dl9zc");
    }
}
