package com.foofinc.cfbra.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfbra.api.dto.CompleteGameDto;
import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.GameDataDto;
import com.foofinc.cfbra.api.dto.SchoolDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CfbApiAccess {

    private final String schoolsUrlString = "https://api.collegefootballdata.com/teams/fbs?year=2022";
    private final String teamGamesUrlString = "https://api.collegefootballdata.com/games/teams?year=2022"/*&seasonType=regular&week=#*/;
    private final String[] gamesUrlString =
            {"https://api.collegefootballdata.com/games?year=2022" /*&week=#&seasonType=regular*/, "&division=fbs"};

    private final List<SchoolDto> schools;
    private final List<Map<Long, CompleteGameDto>> weeks;

    public CfbApiAccess() {
        schools = getAllSchools();
        weeks = getFixturesForSeason();
    }

    public List<Map<Long, CompleteGameDto>> getWeeks() {
        return List.copyOf(weeks);
    }

    public List<SchoolDto> getSchools() {
        return List.copyOf(schools);
    }

    private List<SchoolDto> getAllSchools() {
        return sendGetRequestForAllFBSSchools();
    }

    /*
    Makes number of [weeks in season] + [1 postseason] calls to '/games/teams' and /games
    Returns a list containing maps representing week# that hold data from FixtureDto and GameDataDto wrapped in a
    CompleteGameDto.
     */
    private List<Map<Long, CompleteGameDto>> getFixturesForSeason() {
        String regSeason = "&seasonType=regular";
        String postSeason = "&seasonType=postseason";

        List<Map<Long, CompleteGameDto>> completeGameMap = new ArrayList<>();

        int startingWeek = 1, weeksInSeason = 15;
        for (int i = startingWeek; i <= weeksInSeason; i++) {
            Map<Long, CompleteGameDto> finalListMapCopy = mapToCompleteGameDto(regSeason, i);
            if (finalListMapCopy == null) break;
            completeGameMap.add(finalListMapCopy);
        }

        Map<Long, CompleteGameDto> finalListMapCopy = mapToCompleteGameDto(postSeason, 1);
        completeGameMap.add(finalListMapCopy);


        return completeGameMap;
    }

    private Map<Long, CompleteGameDto> mapToCompleteGameDto(String season, int i) {
        FixtureDto[] tempFixDto = sendGetRequestForWeekFixtures(i, season);
        GameDataDto[] tempGameDataDto = sendGetRequestForWeekGameData(i, season);
        if (tempFixDto.length == 0) {
            return null;
        }

        final Map<Long, CompleteGameDto> tempMap = new HashMap<>();
        for (FixtureDto fixDto : tempFixDto) {
            tempMap.put(fixDto.getId(), new CompleteGameDto(fixDto.getId(), fixDto));
        }
        for (GameDataDto gameDto : tempGameDataDto) {
            if (tempMap.containsKey(gameDto.getId())) {
                tempMap.get(gameDto.getId())
                       .setGameDataDto(gameDto);
            }
        }

        return tempMap.keySet()
                      .stream()
                      .filter(key -> !tempMap.get(key)
                                             .hasNullData())
                      .collect(Collectors.toMap(id -> id, tempMap::get));
    }

    private List<SchoolDto> sendGetRequestForAllFBSSchools() {
        try {
            return mapSchoolsFromJSON(getJSONFromAPI(schoolsUrlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FixtureDto[] sendGetRequestForWeekFixtures(int week, String seasonType) {
        try {
            return mapWeekFromJSON(getJSONFromAPI(teamGamesUrlString + seasonType + "&week=" + week));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private GameDataDto[] sendGetRequestForWeekGameData(int week, String seasonType) {
        try {
            return mapGameDataFromJSON(getJSONFromAPI(gamesUrlString[0] + "&week=" + week + seasonType + gamesUrlString[1]));
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

    private GameDataDto[] mapGameDataFromJSON(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, GameDataDto[].class);
    }

    private String getJSONFromAPI(String s) throws IOException {
        return APICaller.INSTANCE.getJSONFromAPICall(s, "gLQdG5n7YtiTjzu/bxxxd" +
                "+rdzzrhWftHTtIH7PAGVWlAQMOAA7h2ria3ai2Dl9zc");
    }
}
