package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.entity.model.Game;
import com.foofinc.cfbra.entity.model.School;

import java.util.List;
import java.util.Optional;

public class StatisticizedTeam_2 {

    private School school;
    private int rank;
    private Record record;
    private Double weight;


    private int totalOffense;
    private int totalDefense;

    private int pointsAllowed;
    private int pointsFor;

    private int strengthOfSchedule;

    private double pointsForPerGame;
    private double pointsAllowedPerGame;
    private double offensePerGame;
    private double defensePerGame;
    private double strengthOfSchedulePerGame;

    private int gamesPlayed;


    public StatisticizedTeam_2(School school) {
        this.school = school;
        rank = 0;
        record = new Record();
        totalOffense = 0;
        totalDefense = 0;
        pointsAllowed = 0;
        pointsFor = 0;
        strengthOfSchedule = 0;
        weight = 0.0;
        pointsAllowedPerGame = 0.00;
        pointsForPerGame = 0.00;
        offensePerGame = 0.00;
        defensePerGame = 0.00;
        strengthOfSchedulePerGame = -1.0;
        gamesPlayed = 0;
    }

    public static StatisticizedTeam_2 createTeam(School s) {
        return new StatisticizedTeam_2(s);
    }

    public School getSchool() {
        return school;
    }

    /*
    Adder Methods
     */
    public void addToTotalOffense(int offense) {
        this.totalOffense += offense;
    }

    public void addToTotalDefense(int defense) {
        this.totalDefense += defense;
    }

    public void addToPointsAllowed(int pointsAllowed) {
        this.pointsAllowed += pointsAllowed;
    }

    public void addToPointsFor(int pointsFor) {
        this.pointsFor += pointsFor;
    }

    public void addWin() {
        record.incWins();
    }

    public void addLoss() {
        record.incLoses();
    }

    public void addGamesPlayed() {
        gamesPlayed++;
    }

    /*
    Getter Methods- Method names are tightly coupled to Ranking Algo Strings
     */
    public int getWins() {
        return record.getWins();
    }

    public double getPointsForPerGame() {
        pointsForPerGame = (double) pointsFor / gamesPlayed;
        return pointsForPerGame;
    }

    public double getPointsAllowedPerGame() {
        pointsAllowedPerGame = (double) pointsAllowed / gamesPlayed;
        return pointsAllowedPerGame;
    }

    public double getOffensePerGame() {
        offensePerGame = (double) totalOffense / gamesPlayed;
        return offensePerGame;
    }

    public double getDefensePerGame() {
        defensePerGame = (double) totalDefense / gamesPlayed;
        return defensePerGame;
    }

    public double getStrengthOfSchedulePerGame() {
        if (strengthOfSchedulePerGame == -1.0) {
            throw new IllegalStateException("Strength of Schedule has not been calculated");
        }
        return strengthOfSchedulePerGame;
    }

    /*
    Methods used to calculate Strength of schedule
     */
    public double calculateStrengthOfSchedulePerGame(List<StatisticizedTeam_2> partiallyRankedTeams) {
        strengthOfSchedulePerGame = (double) getStrengthOfSchedule(partiallyRankedTeams) / gamesPlayed;
        return strengthOfSchedulePerGame;
    }

    public int getStrengthOfSchedule(List<StatisticizedTeam_2> partiallyRankedTeams) {
        return calculateStrengthOfSchedule(partiallyRankedTeams);

    }

    private int calculateStrengthOfSchedule(List<StatisticizedTeam_2> partiallyRankedTeams) {
        int schedStr = 0;

        List<StatisticizedTeam_2> allTeams = Teams_2.INSTANCE.getTeams();

        for (int i = 0; i < gamesPlayed; i++) {
            Game game = school.getSchedule().get(i);

            StatisticizedTeam_2 opponent = null;

            School homeTeam = game.getHome();
            School awayTeam = game.getAway();

            if (homeTeam == this.school) {
                for (StatisticizedTeam_2 ct : allTeams) {
                    if (ct.getSchool() == awayTeam) {
                        opponent = ct;
                        break;
                    }
                }
            } else {
                for (StatisticizedTeam_2 ct : allTeams) {
                    if (ct.getSchool() == homeTeam) {
                        opponent = ct;
                        break;
                    }
                }
            }

            Optional<StatisticizedTeam_2> optionalOpponents =
                    Optional.of(Optional.ofNullable(opponent)
                                        .orElseGet(() -> new StatisticizedTeam_2(new School(-1,"Null Team", "Nullables",
                                                                                            "Null"))));
            opponent = optionalOpponents.get();


            int index = partiallyRankedTeams.indexOf(opponent);
            int oppStrength = index == -1 ? 132 : index + 1;


            schedStr += oppStrength;

        }
        return schedStr;
    }

    /*
    Setter Methods
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /*
    Utility Methods
     */
    @Override
    public String toString() {

        return "\n#" + rank + " " + school.getSchoolNameString() +
                " " + record +
                " | PF-" + formatDouble(getPointsForPerGame()) +
                " | PA-" + formatDouble(getPointsAllowedPerGame()) +
                " | Offense-" + formatDouble(getOffensePerGame()) +
                " | Defense-" + formatDouble(getDefensePerGame()) +
                " | Schedule_Strength-" + formatDouble(getStrengthOfSchedulePerGame()) +
                " | Weight-" + weight;
    }

    private String formatDouble(double num) {
        return String.format("%.2f", num);
    }

    /*
    Nested Classes
     */
    private static class Record {
        private int wins;
        private int loses;

        Record() {
            wins = 0;
            loses = 0;
        }

        int getWins() {
            return wins;
        }

        void setWins(int wins) {
            this.wins = wins;
        }

        void incWins() {
            wins++;
        }

        void incLoses() {
            loses++;
        }

        int getLoses() {
            return loses;
        }

        void setLoses(int loses) {
            this.loses = loses;
        }

        @Override
        public String toString() {
            return "(" + wins + "-" + loses + ")";
        }
    }
}
