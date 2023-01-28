package com.foofinc.cfbra.entity;

import java.util.ArrayList;
import java.util.List;

/*
Singleton wrapper class
 */
public enum Teams {

    INSTANCE;

    private final List<StatisticizedTeam> statisticizedTeams = new ArrayList<>();

    public List<StatisticizedTeam> getCompleteTeams() {
        return statisticizedTeams;
    }

    @Override
    public String toString() {
        return statisticizedTeams.toString();
    }
}
