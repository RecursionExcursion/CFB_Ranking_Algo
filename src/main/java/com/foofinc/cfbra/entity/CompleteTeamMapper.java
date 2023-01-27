package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.api.dto.StatsDto;
import com.foofinc.cfbra.api.dto.TeamDto;
import com.foofinc.cfbra.entity.model.Game;
import com.foofinc.cfbra.entity.model.School;

public class CompleteTeamMapper {

    private final School school;
    private final StatisticizedTeam statisticizedTeam;


    public CompleteTeamMapper(School school) {
        this.school = school;
        statisticizedTeam = new StatisticizedTeam(school.getSchoolNameString());
        getStatsFromGames();
    }

    private void getStatsFromGames() {
        for (Game game : school.getSchedule()) {

            School thisTeam = game.getHome() == school ? game.getHome() : game.getAway();
            School opposingTeam = game.getHome() != school ? game.getHome() : game.getAway();


            if (game.getHome() == school) {
                statisticizedTeam.addToTotalOffense(game.getHomeYardsGained());
                statisticizedTeam.addToTotalDefense(game.getAwayYardsGained());
                statisticizedTeam.addToPointsFor(game.getHomeScore());
                statisticizedTeam.addToPointsAllowed(game.getAwayScore());

                if (game.getHomeScore() > game.getAwayScore()) {
                    statisticizedTeam.addWin();
                } else {
                    statisticizedTeam.addLoss();
                }
            } else {
                statisticizedTeam.addToTotalOffense(game.getAwayYardsGained());
                statisticizedTeam.addToTotalDefense(game.getHomeYardsGained());
                statisticizedTeam.addToPointsFor(game.getAwayScore());
                statisticizedTeam.addToPointsAllowed(game.getHomeScore());
                if (game.getHomeScore() < game.getAwayScore()) {
                    statisticizedTeam.addWin();
                } else {
                    statisticizedTeam.addLoss();
                }
            }
            statisticizedTeam.addFixture(game);
        }
    }


//    }

    private String getTotalYards(TeamDto thisTeam) {
        for (int i = thisTeam.getStats().length - 1; i >= 0; i--) {
            StatsDto stat = thisTeam.getStats()[i];
            if (stat.getCategory()
                    .equals("totalYards")) {
                return stat.getStat();
            }
        }
        //Return Min.Value to point to show error in stats
        return String.valueOf(Integer.MIN_VALUE);
    }

    public StatisticizedTeam getCompleteTeam() {
        return statisticizedTeam;
    }
}
