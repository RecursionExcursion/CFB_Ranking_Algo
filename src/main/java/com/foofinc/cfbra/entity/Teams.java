package com.foofinc.cfbra.entity;

import java.util.ArrayList;
import java.util.List;

//Singleton wrapper class
public class Teams {

    private final static Teams instance = new Teams();
    private final List<StatisticizedTeam> statisticizedTeams;

    private Teams() {
        this.statisticizedTeams = new ArrayList<>();
    }

    public static Teams getInstance() {
        return instance;
    }

    public List<StatisticizedTeam> getCompleteTeams() {
        return statisticizedTeams;
    }

    @Override
    public String toString() {
        return statisticizedTeams.toString();
    }
}
