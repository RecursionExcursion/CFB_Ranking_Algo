package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.api.CfbApiAccess;
import com.foofinc.cfbra.api.dto.CompleteGameDto;
import com.foofinc.cfbra.entity.*;
import com.foofinc.cfbra.entity.model.ModelGenerator;
import com.foofinc.cfbra.entity.model.School;
import com.foofinc.cfbra.entity.model.SchoolList;
import com.foofinc.cfbra.entity.model.Schools;

import java.util.List;
import java.util.Map;

public class FBSController {

    private static FBSController instance;

    //API to access CFB JSON
    private CfbApiAccess cfbApi;

    //List<Schools> wrapped in class, this object is the serializable form of the data
    private final SchoolList schoolList = SchoolList.INSTANCE;

    //DS holding weeks worth of Fixtures(Jackson DS). (Ex. Week 1, Week 2, etc...)
    private List<Map<Long, CompleteGameDto>> weeks;

    private FBSController() {
        retrieveData();
        List<List<StatisticizedTeam_2>> rankings = new WeeklyRanker().getRankings();
        System.out.println(rankings.get(rankings.size()-1));
    }

    private void retrieveData() {
        LocalMemoryController<Schools> listLocalMemoryController = new LocalMemoryController<>();
        if (listLocalMemoryController.fileExists()) {
            schoolList.loadSchools(listLocalMemoryController.load());
        } else {
            listLocalMemoryController.save((getSchoolsFromAPI()));
        }
    }

    public static FBSController getInstance() {
        if (instance == null) instance = new FBSController();
        return instance;
    }

    public void RankAndPrint() {
//        System.out.println(new RankingAlgo(teams.getCompleteTeams()).rankAndGetTeams());
    }

    private Schools getSchoolsFromAPI() {
        cfbApi = new CfbApiAccess();
        weeks = cfbApi.getWeeks();
        schoolList.loadSchools(mapSchoolsFromAPIToWrapperList());
        parseGames();
        return schoolList.getSchoolsAsSchools();
    }

    private Schools mapSchoolsFromAPIToWrapperList() {

        List<School> schoolList1 = cfbApi.getSchools()
                                         .stream()
                                         .map(ModelGenerator::generateSchool)
                                         .toList();

        return new Schools(schoolList1);
    }

    private void parseGames() {
        for (Map<Long, CompleteGameDto> week : weeks) {
            for (Long id : week.keySet()) {
                ModelGenerator.generateGame(week.get(id));
            }
        }
    }
}
