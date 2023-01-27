package com.foofinc.cfbra.entity.model;

import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.SchoolDto;
import com.foofinc.cfbra.api.dto.StatsDto;
import com.foofinc.cfbra.api.dto.TeamDto;

public class ModelGenerator {

    public static School generateSchool(SchoolDto schoolDto) {
        return new School.Builder().withSchool(schoolDto.getSchool())
                                   .withMascot(schoolDto.getMascot())
                                   .withAbbreviation(schoolDto.getAbbreviation())
                                   .build();
    }

    public static void generateGame(FixtureDto fixtureDto) {

        TeamDto home = fixtureDto.getTeams()[0];
        TeamDto away = fixtureDto.getTeams()[1];

        String homeTeam = home.getSchool();
        int homeScore = home.getPoints();
        int homeYards = Integer.parseInt(getTotalYards(home));

        String awayTeam = away.getSchool();
        int awayScore = away.getPoints();
        int awayYards = Integer.parseInt(getTotalYards(away));

        School homeSchool = getSchoolFromString(homeTeam);
        School awaySchool = getSchoolFromString(awayTeam);


        Game game = new Game.Builder().withHome(homeSchool)
                                      .withHomeScore(homeScore)
                                      .withHomeYardsGained(homeYards)
                                      .withAway(awaySchool)
                                      .withAwayScore(awayScore)
                                      .withAwayYardsGained(awayYards)
                                      .build();

        homeSchool.addGameToSchedule(game);
        awaySchool.addGameToSchedule(game);
    }

    private static School getSchoolFromString(String s) {
        return SchoolList.INSTANCE.getSchoolFromString(s)
                                  .orElse(new School(s, null, null));

    }

    private static String getTotalYards(TeamDto thisTeam) {
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
}
