package com.foofinc.cfbra.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foofinc.cfbra.json.jsondatastructures.School;
import com.foofinc.cfbra.json.jsondatastructures.Fixture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public final class CFB_API {

    //    private final String gamesUrlString = "https://api.collegefootballdata.com/games?year=2022&seasonType=regular&division" +
//            "=fbs";
    private final String schoolsUrlString = "https://api.collegefootballdata.com/teams/fbs?year=2022";

    //Must append "&team=michigan"
    private final String teamGamesUrlString = "https://api.collegefootballdata" +
            ".com/games/teams?year=2022&seasonType=regular";


    //Must append "&team=michigan"
    private final String teamGamesUrlStringTest = "https://api.collegefootballdata" +
            ".com/games/teams?year=2022&seasonType=regular&team=michigan";


    //    private List<Game> games;
    private List<Fixture> games;
    private List<School> schools;


    public void get() {
        try {
            schools = mapSchoolsFromJSON(getJSON(schoolsUrlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Fixture> getGames() {
        return List.copyOf(games);
    }

    public List<School> getSchools() {
        return List.copyOf(schools);
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

    public List<Fixture> getFixtures(int week) {
        try {
            return mapTeamFromJSON(getJSON(teamGamesUrlString + "&week=" + week));
        } catch (IOException e) {
            throw new RuntimeException("Hi"+e);
        }

    }

    private List<Fixture> mapTeamFromJSON(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<>() {
        });
    }
}
