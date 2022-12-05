package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeeklyRanker {

    private final Map<School, List<Fixture>> schoolMap;
    private final List<StatisticizedTeam> statisticizedTeamList;

    public WeeklyRanker(Map<School, List<Fixture>> schoolMap) {
        this.schoolMap = schoolMap;
        statisticizedTeamList = new ArrayList<>();
    }

    private void convertToCompleteTeams() {
        schoolMap.keySet().forEach(school -> {
            statisticizedTeamList.add(new StatisticizedTeam(school.getSchool()));
        });
    }
}
