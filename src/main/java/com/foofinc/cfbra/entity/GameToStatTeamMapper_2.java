package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.entity.model.Game;

public class GameToStatTeamMapper_2 {

    private final StatisticizedTeam_2 team_2;


    public GameToStatTeamMapper_2(Game game, StatisticizedTeam_2 team_2) {
        this.team_2 = team_2;
        getStatsFromGame(game);
    }

    private void getStatsFromGame(Game game) {

        if (game.getHome() == team_2.getSchool()) {
            team_2.addToTotalOffense(game.getHomeYardsGained());
            team_2.addToTotalDefense(game.getAwayYardsGained());
            team_2.addToPointsFor(game.getHomeScore());
            team_2.addToPointsAllowed(game.getAwayScore());

            if (game.getHomeScore() > game.getAwayScore()) {
                team_2.addWin();
            } else {
                team_2.addLoss();
            }
        } else {
            team_2.addToTotalOffense(game.getAwayYardsGained());
            team_2.addToTotalDefense(game.getHomeYardsGained());
            team_2.addToPointsFor(game.getAwayScore());
            team_2.addToPointsAllowed(game.getHomeScore());
            if (game.getHomeScore() < game.getAwayScore()) {
                team_2.addWin();
            } else {
                team_2.addLoss();
            }
        }
        team_2.addGamesPlayed();
    }


    public StatisticizedTeam_2 getCompleteTeam() {
        return team_2;
    }
}
