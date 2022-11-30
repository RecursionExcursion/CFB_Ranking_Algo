package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.api.CFB_API;
import com.foofinc.cfbra.entity.CompleteTeam;
import com.foofinc.cfbra.entity.RankingAlgo;
import com.foofinc.cfbra.json.CompleteTeamMapper;
import com.foofinc.cfbra.json.Teams;
import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;
import com.foofinc.cfbra.json.jsondatastructures.Team;

import java.util.*;

public class Controller {

    //API to access CFB JSON
    private final CFB_API cfbApi;

    //Rudimentary DS to hold School(Jackson DS) and List<Fixture(Jackson DS)>
    private final Map<School, List<Fixture>> schoolMap;

    //List<CompletedSchools> wrapped in class
    private final Teams teams;

    //DS holding weeks worth of Fixtures(Jackson DS). (Ex. Week 1, Week 2, etc...)
    //TODO Smell? Should 'weeks' being in its own wrapper class?
    private List<List<Fixture>> weeks;


    public Controller() {
        cfbApi = new CFB_API();
        schoolMap = new HashMap<>();
        teams = new Teams();
        weeks = new ArrayList<>();

        mapAPIDataToCompletedSchools();

        RankingAlgo rankingAlgo = initializeRA();
        System.out.println(rankingAlgo);
    }


    private void mapAPIDataToCompletedSchools() {
        mapSchoolsFromAPIToMap();
        getFixtures();
        parseGames();
        completeSchools();
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
