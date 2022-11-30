package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.CompleteTeam;

import java.util.ArrayList;
import java.util.List;

//Singleton wrapper class
public class Teams {

    private final static Teams instance = new Teams();
    private final List<CompleteTeam> completeTeams;

    private Teams() {
        this.completeTeams = new ArrayList<>();
    }

    public static Teams getInstance() {
        return instance;
    }

    public List<CompleteTeam> getCompleteTeams() {
        return completeTeams;
    }

    @Override
    public String toString() {
        return completeTeams.toString();
    }
}
