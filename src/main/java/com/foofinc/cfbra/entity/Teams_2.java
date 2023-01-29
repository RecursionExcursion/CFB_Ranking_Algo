package com.foofinc.cfbra.entity;

import java.util.ArrayList;
import java.util.List;

/*
Singleton wrapper class
 */
public enum Teams_2 {

    INSTANCE;

    private List<StatisticizedTeam_2> statisticizedTeams = new ArrayList<>();

    public List<StatisticizedTeam_2> getTeams() {
        return statisticizedTeams;
    }

    public void loadTeams(List<StatisticizedTeam_2> t) {
        statisticizedTeams = t;

    }

    @Override
    public String toString() {
        return statisticizedTeams.toString();
    }
}
