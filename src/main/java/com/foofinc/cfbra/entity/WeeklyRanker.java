package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeeklyRanker {

    private final Map<School, List<Fixture>> schoolMap;
    private final List<CompleteTeam> completeTeamList;

    public WeeklyRanker(Map<School, List<Fixture>> schoolMap) {
        this.schoolMap = schoolMap;
        completeTeamList = new ArrayList<>();
    }

    private void convertToCompleteTeams() {
        schoolMap.keySet().forEach(school -> {
            completeTeamList.add(new CompleteTeam(school.getSchool()));
        });
    }
}
