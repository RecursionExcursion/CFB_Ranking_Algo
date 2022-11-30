package com.foofinc.cfbra.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class CFB_API {

    private final String schoolsUrlString = "https://api.collegefootballdata.com/teams/fbs?year=2022";

    //Must append "&team=michigan"
    private final String teamGamesUrlString = "https://api.collegefootballdata" +
            ".com/games/teams?year=2022&seasonType=regular";


    private List<School> schools;
    private final List<Fixture[]> weeks = new ArrayList<>();


    public CFB_API() {
        getAllSchools();
        getFixturesForAllWeeks();
    }


    private void getAllSchools() {
        schools = sendGetRequestForAllAFBSSchools();
    }

    private void getFixturesForAllWeeks() {
        int startingWeek = 1, weeksInSeason = 13;
        for (int i = startingWeek; i <= weeksInSeason; i++) {
            weeks.add(sendGetRequestForWeekFixtures(i));
        }
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

    private String getJSON(String s) throws IOException {
        URL url = new URL(s);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization", "Bearer " + "gLQdG5n7YtiTjzu/bxxxd+rdzzrhWftHTtIH7PAGVWlAQMOAA7h2ria3ai2Dl9zc");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();

        int respCode = conn.getResponseCode();
        System.out.println(respCode);

        if (respCode != 200) {
            throw new RuntimeException("Http Response Code- " + respCode);
        } else {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()) sb.append(scanner.nextLine());
            return sb.toString();
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

    public List<Fixture[]> getWeeks() {
        return List.copyOf(weeks);
    }

    public List<School> getSchools() {
        return List.copyOf(schools);
    }
}
