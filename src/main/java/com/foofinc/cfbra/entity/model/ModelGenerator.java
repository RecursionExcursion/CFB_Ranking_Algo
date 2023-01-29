package com.foofinc.cfbra.entity.model;

import com.foofinc.cfbra.api.dto.*;

public class ModelGenerator {

    public static School generateSchool(SchoolDto schoolDto) {
        return new School.Builder().withId(schoolDto.getId())
                                   .withSchool(schoolDto.getSchool())
                                   .withMascot(schoolDto.getMascot())
                                   .withAbbreviation(schoolDto.getAbbreviation())
                                   .build();
    }

    public static void generateGame(CompleteGameDto completeGameDto) {

        TeamDto away = completeGameDto.getFixtureDto()
                                      .getTeams()[0];
        TeamDto home = completeGameDto.getFixtureDto()
                                      .getTeams()[1];


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


        Game game = new Game.Builder().withId(completeGameDto.getFixtureDto()
                                                             .getId())
                                      .withHome(homeSchool)
                                      .withHomeScore(homeScore)
                                      .withHomeYardsGained(homeYards)
                                      .withAway(awaySchool)
                                      .withAwayScore(awayScore)
                                      .withAwayYardsGained(awayYards)
                                      .withSeason(completeGameDto.getGameDataDto()
                                                                 .getSeason())
                                      .withWeek(completeGameDto.getGameDataDto()
                                                               .getWeek())
                                      .withSeason_type(completeGameDto.getGameDataDto()
                                                                      .getSeason_type())
                                      .withStart_date(completeGameDto.getGameDataDto()
                                                                     .getStart_date())
                                      .withHome_id(completeGameDto.getGameDataDto()
                                                                  .getHome_id())
                                      .withAway_id(completeGameDto.getGameDataDto()
                                                                  .getAway_id())
                                      .build();

        homeSchool.addGameToSchedule(game);
        awaySchool.addGameToSchedule(game);
    }

    private static School getSchoolFromString(String schoolName) {
        return SchoolList.INSTANCE.getSchoolFromString(schoolName)
                                  .orElse(new School(-1, schoolName, null, null));

    }

    private static String getTotalYards(TeamDto thisTeam) {
        for (int i = thisTeam.getStats().length - 1; i >= 0; i--) {
            StatsDto stat = thisTeam.getStats()[i];
            if (stat.getCategory()
                    .equals("totalYards")) {
                return stat.getStat();
            }
        }
        //Return Min.Value to point to show error in stats, ex. Ohio State ~-2147483648 yards.
        return String.valueOf(Integer.MIN_VALUE);
    }
}
