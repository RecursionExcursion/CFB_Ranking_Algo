package com.foofinc.cfbra.json;

import com.foofinc.cfbra.entity.CompleteTeam;

import java.util.ArrayList;
import java.util.List;


public class Teams {

    private final List<CompleteTeam> completeTeams;

    public Teams() {
        this.completeTeams = new ArrayList<>();
    }

    public List<CompleteTeam> getCompleteTeams() {
        return completeTeams;
    }

    @Override
    public String toString() {
        return completeTeams.toString();
    }
}
