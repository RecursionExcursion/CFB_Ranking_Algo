package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.api.CfbApiAccess;
import com.foofinc.cfbra.entity.CompleteTeam;
import com.foofinc.cfbra.entity.RankingAlgo;
import com.foofinc.cfbra.json.CompleteTeamMapper;
import com.foofinc.cfbra.json.SchoolAndFixturesDS;
import com.foofinc.cfbra.json.Teams;
import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;
import com.foofinc.cfbra.json.jsondatastructures.Team;
import com.foofinc.cfbra.persistence.MemoryManager;

import java.util.*;

public class Controller {

    //API to access CFB JSON
    private CfbApiAccess cfbApi;

    //Rudimentary DS to hold School(Jackson DS) and List<Fixture(Jackson DS)>
    private SchoolAndFixturesDS schoolMap;

    //List<CompletedSchools> wrapped in class
    private final Teams teams;

    //DS holding weeks worth of Fixtures(Jackson DS). (Ex. Week 1, Week 2, etc...)
    private List<List<Fixture>> weeks;


    public Controller() {

        //TODO Refactor

        teams = Teams.getInstance();

        MemoryManager memoryManager = new MemoryManager();
        Map<School, List<Fixture>> schoolMapFromMemory = memoryManager.loadSchools();

        if (schoolMapFromMemory == null) {
            cfbApi = new CfbApiAccess();
            schoolMap = new SchoolAndFixturesDS();
            weeks = new ArrayList<>();
            mapAPIDataToCompletedSchools();

            memoryManager.saveSchools(schoolMap);
        } else {
            schoolMap = new SchoolAndFixturesDS(schoolMapFromMemory);
        }
        completeSchools();

        RankingAlgo rankingAlgo = initializeRA();
        System.out.println(rankingAlgo);
    }


    private void mapAPIDataToCompletedSchools() {
        mapSchoolsFromAPIToMap();
        getFixtures();
        parseGames();
    }

    private void mapSchoolsFromAPIToMap() {
        cfbApi.getSchools().forEach(s -> schoolMap.putIfAbsent(s, new ArrayList<>()));
    }

    private void getFixtures() {
        weeks = cfbApi.getWeeks().stream()
                      .map(List::of)
                      .toList();
    }

    private void parseGames() {
        for (List<Fixture> week : weeks) {
            for (Fixture fix : week) {

                Team team0 = fix.getTeams()[0];
                Team team1 = fix.getTeams()[1];

                for (School school : schoolMap.keySet()) {
                    if (school.getSchool().equals(team0.getSchool()) ||
                            school.getSchool().equals(team1.getSchool())) {
                        schoolMap.get(school).add(fix);
                    }
                }
            }
        }
    }

    private void completeSchools() {
        for (Map.Entry<School, List<Fixture>> entry : schoolMap.entrySet()) {
            teams.getCompleteTeams().add(new CompleteTeamMapper(entry).getCompleteTeam());
        }
    }

    private RankingAlgo initializeRA() {
        RankingAlgo rankingAlgo = new RankingAlgo();
        for (CompleteTeam team : teams.getCompleteTeams()) {
            rankingAlgo.addTeam(team);
        }
        rankingAlgo.rankTeams();
        return rankingAlgo;
    }
}
