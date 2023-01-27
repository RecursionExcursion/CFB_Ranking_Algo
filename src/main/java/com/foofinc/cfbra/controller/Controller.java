package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.api.CfbApiAccess;
import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.entity.CompleteTeamMapper;
import com.foofinc.cfbra.entity.RankingAlgo;
import com.foofinc.cfbra.entity.SchoolAndFixturesDS;
import com.foofinc.cfbra.entity.Teams;
import com.foofinc.cfbra.entity.model.ModelGenerator;
import com.foofinc.cfbra.entity.model.School;
import com.foofinc.cfbra.entity.model.SchoolList;
import com.foofinc.cfbra.persistence.MemoryManager;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static Controller instance;

    //API to access CFB JSON
    private CfbApiAccess cfbApi;

    //Rudimentary DS to hold School(Jackson DS) and List<Fixture(Jackson DS)>
    private SchoolAndFixturesDS schoolMap;

    //List<CompletedSchools> wrapped in class
    private final Teams teams;

    //DS holding weeks worth of Fixtures(Jackson DS). (Ex. Week 1, Week 2, etc...)
    private List<List<FixtureDto>> weeks;

    //List<Schools> wrapped in class
    private SchoolList schoolList;


    private Controller() {

        //TODO Refactor

        schoolList = SchoolList.INSTANCE;
        teams = Teams.getInstance();

        MemoryManager memoryManager = new MemoryManager();
        if (memoryManager.fileExists()) {
            schoolList.loadSchools(memoryManager.loadSchools());
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
//        schoolMap = new SchoolAndFixturesDS();
        schoolList = SchoolList.INSTANCE;
        weeks = new ArrayList<>();

        mapAPIDataToCompletedSchools();
        memoryManager.saveSchools(schoolList.getSchools());
    }


    private void mapAPIDataToCompletedSchools() {
        mapSchoolsFromAPIToWrapperList();
        getFixtures();
        List<School> test = SchoolList.INSTANCE.getSchools();
        parseGames();
    }

    private void mapSchoolsFromAPIToWrapperList() {
        cfbApi.getSchools()
              .stream()
              .map(ModelGenerator::generateSchool)
              .forEach(school -> schoolList.addSchool(school));

    }

    private void getFixtures() {
        weeks = cfbApi.getWeeks()
                      .stream()
                      .map(List::of)
                      .toList();
    }

    private void parseGames() {
        for (List<FixtureDto> week : weeks) {
            for (FixtureDto fix : week) {
                ModelGenerator.generateGame(fix);
//
//                TeamDto team0 = fix.getTeams()[0];
//                TeamDto team1 = fix.getTeams()[1];
//
//                for (SchoolDto school : schoolMap.keySet()) {
//                    if (school.getSchool()
//                              .equals(team0.getSchool()) ||
//                            school.getSchool()
//                                  .equals(team1.getSchool())) {
//                        schoolMap.get(school)
//                                 .add(fix);
//                    }
//                }
            }
        }
    }

    private void completeSchools() {
        for (School s : schoolList.getSchools()) {
            teams.getCompleteTeams()
                 .add(new CompleteTeamMapper(s).getCompleteTeam());
        }
    }

    private RankingAlgo initializeRA() {
        RankingAlgo rankingAlgo = new RankingAlgo(teams.getCompleteTeams());
        rankingAlgo.rankTeams();
        return rankingAlgo;
    }
}
