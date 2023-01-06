package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.api.CfbApiAccess;
import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;
import com.foofinc.cfbra.api.jsondatastructures.Team;
import com.foofinc.cfbra.entity.CompleteTeamMapper;
import com.foofinc.cfbra.entity.RankingAlgo;
import com.foofinc.cfbra.entity.SchoolAndFixturesDS;
import com.foofinc.cfbra.entity.Teams;
import com.foofinc.cfbra.persistence.MemoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {

    private static Controller instance;

    //API to access CFB JSON
    private CfbApiAccess cfbApi;

    //Rudimentary DS to hold School(Jackson DS) and List<Fixture(Jackson DS)>
    private SchoolAndFixturesDS schoolMap;

    //List<CompletedSchools> wrapped in class
    private final Teams teams;

    //DS holding weeks worth of Fixtures(Jackson DS). (Ex. Week 1, Week 2, etc...)
    private List<List<Fixture>> weeks;


    private Controller() {

        //TODO Refactor

        teams = Teams.getInstance();

        MemoryManager memoryManager = new MemoryManager();
        if (memoryManager.fileExists()) {
            schoolMap = memoryManager.loadSchools();
        } else {
            saveSchoolsFromAPIToLocalMemory(memoryManager);
        }
        completeSchools();

    }

    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    public void RankAndPrint() {
        RankingAlgo rankingAlgo = initializeRA();
        System.out.println(rankingAlgo);
    }

    private void saveSchoolsFromAPIToLocalMemory(MemoryManager memoryManager) {
        cfbApi = new CfbApiAccess();
        schoolMap = new SchoolAndFixturesDS();
        weeks = new ArrayList<>();
        mapAPIDataToCompletedSchools();
        memoryManager.saveSchools(schoolMap);
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
        weeks = cfbApi.getWeeks()
                      .stream()
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
        RankingAlgo rankingAlgo = new RankingAlgo(teams.getCompleteTeams());
        rankingAlgo.rankTeams();
        return rankingAlgo;
    }
}
