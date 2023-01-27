package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.SchoolDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeeklyRanker {

    private final Map<SchoolDto, List<FixtureDto>> schoolMap;
    private final List<StatisticizedTeam> statisticizedTeamList;

    public WeeklyRanker(Map<SchoolDto, List<FixtureDto>> schoolMap) {
        this.schoolMap = schoolMap;
        statisticizedTeamList = new ArrayList<>();
    }

    private void convertToCompleteTeams() {
        schoolMap.keySet().forEach(school -> {
            statisticizedTeamList.add(new StatisticizedTeam(school.getSchool()));
        });
    }
}
