package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.api.CfbApiAccess;
import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.entity.RankingAlgo;
import com.foofinc.cfbra.entity.SchoolList;
import com.foofinc.cfbra.entity.SchoolToStatTeamMapper;
import com.foofinc.cfbra.entity.Teams;
import com.foofinc.cfbra.entity.model.ModelGenerator;
import com.foofinc.cfbra.entity.model.School;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static Controller instance;

    //API to access CFB JSON
    private CfbApiAccess cfbApi;

    //List<CompletedSchools> wrapped in class
    private final Teams teams = Teams.INSTANCE;

    //List<Schools> wrapped in class, this object is the serializable form of the data
    private final SchoolList schoolList = SchoolList.INSTANCE;;

    //DS holding weeks worth of Fixtures(Jackson DS). (Ex. Week 1, Week 2, etc...)
    private List<List<FixtureDto>> weeks;

    private Controller() {

        //TODO Refactor
        LocalMemoryController<ArrayList<School>> listLocalMemoryController = new LocalMemoryController<>();
        if (listLocalMemoryController.fileExists()) {
            schoolList.loadSchools(listLocalMemoryController.load());
        } else {
            saveSchoolsFromAPIToLocalMemory(listLocalMemoryController);
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

    private void saveSchoolsFromAPIToLocalMemory(LocalMemoryController<ArrayList<School>> memorySerializationManager) {
        cfbApi = new CfbApiAccess();
        weeks = new ArrayList<>();
        mapAPIDataToCompletedSchools();
        memorySerializationManager.save((ArrayList<School>) schoolList.getSchools());
    }


    private void mapAPIDataToCompletedSchools() {
        mapSchoolsFromAPIToWrapperList();
        getFixtures();
        parseGames();
    }

    private void mapSchoolsFromAPIToWrapperList() {
        cfbApi.getSchools()
              .stream()
              .map(ModelGenerator::generateSchool)
              .forEach(schoolList::addSchool);
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
            }
        }
    }

    private void completeSchools() {
        for (School s : schoolList.getSchools()) {
            teams.getCompleteTeams()
                 .add(new SchoolToStatTeamMapper(s).getCompleteTeam());
        }
    }

    private RankingAlgo initializeRA() {
        RankingAlgo rankingAlgo = new RankingAlgo(teams.getCompleteTeams());
        rankingAlgo.rankTeams();
        return rankingAlgo;
    }
}
