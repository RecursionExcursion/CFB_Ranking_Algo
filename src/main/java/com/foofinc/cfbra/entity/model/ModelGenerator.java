package com.foofinc.cfbra.entity.model;

import com.foofinc.cfbra.api.dto.FixtureDto;
import com.foofinc.cfbra.api.dto.SchoolDto;
import com.foofinc.cfbra.api.dto.StatsDto;
import com.foofinc.cfbra.api.dto.TeamDto;
import com.foofinc.cfbra.entity.SchoolList;

public class ModelGenerator {

    public static School generateSchool(SchoolDto schoolDto) {
        return new School.Builder().withSchool(schoolDto.getSchool())
                                   .withMascot(schoolDto.getMascot())
                                   .withAbbreviation(schoolDto.getAbbreviation())
                                   .build();
    }

    public static void generateGame(FixtureDto fixtureDto) {

        TeamDto away = fixtureDto.getTeams()[0];
        TeamDto home = fixtureDto.getTeams()[1];

        //Home Stats
        String homeTeamName = home.getSchool();
        int homeScore = home.getPoints();
        int homeYards = Integer.parseInt(getTotalYards(home));

        //Away Stats
        String awayTeamName = away.getSchool();
        int awayScore = away.getPoints();
        int awayYards = Integer.parseInt(getTotalYards(away));

        School homeSchool = getSchoolFromString(homeTeamName);
        School awaySchool = getSchoolFromString(awayTeamName);


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

    private static School getSchoolFromString(String schoolName) {
        return SchoolList.INSTANCE.getSchoolFromString(schoolName)
                                  .orElse(new School(schoolName, null, null));

    }

    private static String getTotalYards(TeamDto thisTeam) {
        for (int i = thisTeam.getStats().length - 1; i >= 0; i--) {
            StatsDto stat = thisTeam.getStats()[i];
            if (stat.getCategory()
                    .equals("totalYards")) {
                return stat.getStat();
            }
        }
        //Return Min.Value to point to show error in stats, ex. Ohio State -3900000 yards.
        return String.valueOf(Integer.MIN_VALUE);
    }
}
