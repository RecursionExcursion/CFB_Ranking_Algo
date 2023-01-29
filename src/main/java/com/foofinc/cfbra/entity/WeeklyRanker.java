package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.entity.model.Game;
import com.foofinc.cfbra.entity.model.School;
import com.foofinc.cfbra.entity.model.SchoolList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WeeklyRanker {

    List<School> schools = SchoolList.INSTANCE.getSchoolsAsList();


    List<StatisticizedTeam_2> statisticizedTeam_2List = schools.stream()
                                                               .map(StatisticizedTeam_2::createTeam)
                                                               .toList();


    List<List<StatisticizedTeam_2>> weeklyRankings = new ArrayList<>();

    public List<List<StatisticizedTeam_2>> getRankings() {
        Teams_2.INSTANCE.loadTeams(statisticizedTeam_2List);
        initialize();
        return weeklyRankings;
    }

    private void initialize() {
        List<StatisticizedTeam_2> lastWeek = null;

        String regularSeason = "regular";
        String postSeason = "postseason";

        for (int i = 1; i <= 16; i++) {
            rankTeamsForWeek(i, regularSeason);
            List<StatisticizedTeam_2> statisticizedTeam2s =
                    new RankingAlgo_2(statisticizedTeam_2List, lastWeek).rankAndGetTeams();
            weeklyRankings.add(statisticizedTeam2s);
            lastWeek = weeklyRankings.get(weeklyRankings.size() - 1);
        }
        rankTeamsForWeek(1, postSeason);
        List<StatisticizedTeam_2> statisticizedTeam2s =
                new RankingAlgo_2(statisticizedTeam_2List, lastWeek).rankAndGetTeams();
        weeklyRankings.add(statisticizedTeam2s);
    }


    public void rankTeamsForWeek(int week, String seasonType) {
        List<Game> gamesForWeek = getGamesForWeek(week, seasonType);

        for (Game g : gamesForWeek) {
            StatisticizedTeam_2 homeTeam = findStatTeamFromSchool(g.getHome());
            StatisticizedTeam_2 awayTeam = findStatTeamFromSchool(g.getAway());

            if (homeTeam != null) {
                new GameToStatTeamMapper_2(g, homeTeam);
            }
            if (awayTeam != null) {
                new GameToStatTeamMapper_2(g, awayTeam);
            }
        }


    }

    private StatisticizedTeam_2 findStatTeamFromSchool(School school) {
        for (StatisticizedTeam_2 st : statisticizedTeam_2List) {
            if (st.getSchool() == school) return st;
        }
        return null;
    }


    private List<Game> getGamesForWeek(int week, String seasonType) {

        Set<Game> games2 = new HashSet<>();
        for (School school : schools) {
            List<Game> schedule = school.getSchedule();
            for (Game game : schedule) {
                if (game.getWeek() == week && game.getSeason_type()
                                                  .equals(seasonType)) {
                    games2.add(game);
                }
            }
        }


        /*
        //TODO DELETE
        Command line tester logic
        */
        for (Game g : games2) {
            String teamName = "Hawai'i";
            if (
                    g.getHome()
                     .getSchoolNameString()
                     .equals(teamName) ||
                            g.getAway()
                             .getSchoolNameString()
                             .equals(teamName)
            ) {
                System.out.println(g.getId() + "  " + g.getHome().getSchoolNameString() + " vs " + g.getAway().getSchoolNameString());
            }
        }
        /**/

        return new ArrayList<>(games2);

    }
}
