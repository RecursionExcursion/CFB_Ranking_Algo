package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.entity.model.Game;
import com.foofinc.cfbra.entity.model.School;

import java.util.List;
import java.util.Optional;

public class StatisticizedTeam {

    private School school;
    private int rank;
    private Record record;
    private Double weight;

    private final List<Game> schedule;

    private int totalOffense;
    private int totalDefense;

    private int pointsAllowed;
    private int pointsFor;

    private int strengthOfSchedule;

    //TODO Add conference stats????

    private double pointsForPerGame;
    private double pointsAllowedPerGame;
    private double offensePerGame;
    private double defensePerGame;
    private double strengthOfSchedulePerGame;


    public StatisticizedTeam(School school) {
        this.school = school;
        rank = 0;
        record = new Record();
        schedule = school.getSchedule();
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
    }


    public double getPointsForPerGame() {
        pointsForPerGame = (double) pointsFor / schedule.size();
        return pointsForPerGame;
    }

    public double getPointsAllowedPerGame() {
        pointsAllowedPerGame = (double) pointsAllowed / schedule.size();
        return pointsAllowedPerGame;
    }

    public double getOffensePerGame() {
        offensePerGame = (double) totalOffense / schedule.size();
        return offensePerGame;
    }

    public double getDefensePerGame() {
        defensePerGame = (double) totalDefense / schedule.size();
        return defensePerGame;
    }

    public double calculateStrengthOfSchedulePerGame(List<StatisticizedTeam> partiallyRankedTeams) {
        if (strengthOfSchedulePerGame == -1.0) {
            strengthOfSchedulePerGame =
                    (double) getStrengthOfSchedule(partiallyRankedTeams) / schedule.size();
        }
        return strengthOfSchedulePerGame;
    }

    public double getStrengthOfSchedulePerGame() {
        if (strengthOfSchedulePerGame == -1.0) {
            throw new IllegalStateException("Strength of Schedule has not been calculated");
        }
        return strengthOfSchedulePerGame;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

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

    public int getWins() {
        return record.getWins();
    }

    public School getSchool() {
        return school;
    }

    public int getRank() {
        return rank;
    }

    public Record getRecord() {
        return record;
    }

    public List<Game> getSchedule() {
        return schedule;
    }

    public int getTotalOffense() {
        return totalOffense;
    }

    public int getTotalDefense() {
        return totalDefense;
    }

    public int getPointsAllowed() {
        return pointsAllowed;
    }

    public int getPointsFor() {
        return pointsFor;
    }

    public int getStrengthOfSchedule(List<StatisticizedTeam> partiallyRankedTeams) {
        return calculateStrengthOfSchedule(partiallyRankedTeams);

    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    private int calculateStrengthOfSchedule(List<StatisticizedTeam> partiallyRankedTeams) {
        int schedStr = 0;

        List<StatisticizedTeam> allTeams = Teams.INSTANCE.getCompleteTeams();

        for (Game game : schedule) {

            StatisticizedTeam opponent = null;

            School homeTeam = game.getHome();
            School awayTeam = game.getAway();

            if (homeTeam == this.school) {
                for (StatisticizedTeam ct : allTeams) {
                    if (ct.getSchool() == awayTeam) {
                        opponent = ct;
                        break;
                    }
                }
            } else {
                for (StatisticizedTeam ct : allTeams) {
                    if (ct.getSchool() == homeTeam) {
                        opponent = ct;
                        break;
                    }
                }
            }

            Optional<StatisticizedTeam> optionalOpponents =
                    Optional.of(Optional.ofNullable(opponent)
                                        .orElseGet(() -> new StatisticizedTeam(new School("Null Team", "Nullables",
                                                                                          "Null"))));
            opponent = optionalOpponents.get();


            int index = partiallyRankedTeams.indexOf(opponent);
            int oppStrength = index == -1 ? 132 : index + 1;


            schedStr += oppStrength;

        }
        return schedStr;
    }

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
