package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;
import com.foofinc.cfbra.api.jsondatastructures.Stats;
import com.foofinc.cfbra.api.jsondatastructures.Team;

import java.util.List;
import java.util.Map;

public class CompleteTeamMapper {

    private final Map.Entry<School, List<Fixture>> entry;
    private final StatisticizedTeam statisticizedTeam;


    public CompleteTeamMapper(Map.Entry<School, List<Fixture>> entry) {
        this.entry = entry;
        statisticizedTeam = new StatisticizedTeam(this.entry.getKey().getSchool());
        getStatsFromGames();
    }

    private void getStatsFromGames() {
        for (Fixture fix : entry.getValue()) {
            boolean is0thTeam = fix.getTeams()[0].getSchool().equals(entry.getKey().getSchool());

            Team thisTeam = is0thTeam ? fix.getTeams()[0] : fix.getTeams()[1];
            Team opposingTeam = !is0thTeam ? fix.getTeams()[0] : fix.getTeams()[1];


            String totalDefYards = String.valueOf(Integer.MIN_VALUE);
            String totalOffYards = String.valueOf(Integer.MIN_VALUE);

            totalDefYards = getTotalYards(opposingTeam, totalDefYards);
            totalOffYards = getTotalYards(thisTeam, totalOffYards);


            int thisTeamPF = thisTeam.getPoints();
            int thisTeamPA = opposingTeam.getPoints();

            assert totalOffYards != null;
            assert totalDefYards != null;
            statisticizedTeam.addToTotalOffense(Integer.parseInt(totalOffYards));
            statisticizedTeam.addToTotalDefense(Integer.parseInt(totalDefYards));
            statisticizedTeam.addToPointsFor(thisTeamPF);
            statisticizedTeam.addToPointsAllowed(thisTeamPA);

            if (thisTeamPF > thisTeamPA) {
                statisticizedTeam.addWin();
            } else {
                statisticizedTeam.addLoss();
            }
            statisticizedTeam.addFixture(fix);
        }
    }

    private String getTotalYards(Team thisTeam, String totalOffYards) {
        for (int i = thisTeam.getStats().length - 1; i >= 0; i--) {
            Stats stat = thisTeam.getStats()[i];
            if (stat.getCategory().equals("totalYards")) {
                totalOffYards = stat.getStat();
                break;
            }
        }
        return totalOffYards;
    }

    public StatisticizedTeam getCompleteTeam() {
        return statisticizedTeam;
    }
}
