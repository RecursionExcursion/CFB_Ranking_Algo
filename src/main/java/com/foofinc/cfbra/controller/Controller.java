package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.api.CFB_API;
import com.foofinc.cfbra.entity.CompleteTeam;
import com.foofinc.cfbra.entity.RankingAlgo;
import com.foofinc.cfbra.json.CompleteTeamMapper;
import com.foofinc.cfbra.json.Teams;
import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;
import com.foofinc.cfbra.json.jsondatastructures.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private final List<List<Fixture>> weeks;
    private final CFB_API cfbApi;
    private final Map<School, List<Fixture>> schoolMap;
    Teams teams;


    public Controller() {
        cfbApi = new CFB_API();
        cfbApi.get();

        weeks = new ArrayList<>();
        schoolMap = new HashMap<>();
        cfbApi.getSchools().forEach(s -> schoolMap.putIfAbsent(s, new ArrayList<>()));
        teams = new Teams();

        getFixtures();
        parseGames();
        completeSchools();

        RankingAlgo rankingAlgo = new RankingAlgo();
        for(CompleteTeam team:teams.getCompleteTeams()){
            rankingAlgo.addTeam(team);
        }
        rankingAlgo.rankTeams();
        System.out.println(rankingAlgo);
    }

    private void getFixtures() {
        for (int i = 1; i <= 13; i++) {
            weeks.add(cfbApi.getFixtures(i));
        }
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

    private void completeSchools(){
        for(Map.Entry<School,List<Fixture>> entry: schoolMap.entrySet()){
            teams.getCompleteTeams().add(new CompleteTeamMapper(entry).getCompleteTeam());
        }
    }

}
