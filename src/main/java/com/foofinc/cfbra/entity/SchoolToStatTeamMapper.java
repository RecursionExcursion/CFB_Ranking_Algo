package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.entity.model.Game;
import com.foofinc.cfbra.entity.model.School;

public class SchoolToStatTeamMapper {

    private final School school;
    private final StatisticizedTeam statisticizedTeam;


    public SchoolToStatTeamMapper(School school) {
        this.school = school;
        statisticizedTeam = new StatisticizedTeam(school);
        getStatsFromGames();
    }

    private void getStatsFromGames() {
        for (Game game : school.getSchedule()) {

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
        }
    }

    public StatisticizedTeam getCompleteTeam() {
        return statisticizedTeam;
    }
}
