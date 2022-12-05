package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatisticizedTeam {

    private String name;
    private int rank;
    private Record record;
    private Double weight;

    private Schedule schedule;

    private int totalOffense;
    private int totalDefense;

    private int pointsAllowed;
    private int pointsFor;

    private int strengthOfSchedule;

    //TODO Add conference stats????

    public StatisticizedTeam(String name) {
        this.name = name;
        rank = 0;
        record = new Record();
        schedule = new Schedule();
        totalOffense = 0;
        totalDefense = 0;
        pointsAllowed = 0;
        pointsFor = 0;
        strengthOfSchedule = 0;
        weight = 0.0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public void addFixture(Fixture fixture) {
        schedule.games.add(fixture);
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

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public Record getRecord() {
        return record;
    }

    public Schedule getSchedule() {
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

    public int getStrengthOfSchedule() {
        strengthOfSchedule = calculateStrOfSched();
        return strengthOfSchedule;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    private int calculateStrOfSched() {
        int schedStr = 0;

        List<StatisticizedTeam> allTeams = Teams.getInstance().getCompleteTeams();

        for (Fixture fix : schedule.games) {

            StatisticizedTeam opponent = null;

            String team0Name = fix.getTeams()[0].getSchool();
            String team1Name = fix.getTeams()[1].getSchool();


            if (team0Name.equals(this.name)) {
                for (StatisticizedTeam ct : allTeams) {
                    if (ct.name.equals(team1Name)) {
                        opponent = ct;
                        break;
                    }
                }
            } else {
                for (StatisticizedTeam ct : allTeams) {
                    if (ct.name.equals(team0Name)) {
                        opponent = ct;
                        break;
                    }
                }
            }

            Optional<StatisticizedTeam> optionalOpponents =
                    Optional.of(Optional.ofNullable(opponent).orElseGet(() -> new StatisticizedTeam("Null Team")));
            opponent = optionalOpponents.get();
            schedStr += opponent.getWins();

        }
        return schedStr;
    }

    @Override
    public String toString() {
        return "\n#" + rank + " " + name +
                " " + record +
                " | PF-" + pointsFor +
                " | PA-" + pointsAllowed +
                " | Offense-" + totalOffense +
                " | Defense-" + totalDefense +
                " | Schedule_Strength-" + strengthOfSchedule +
                " | Weight-" + weight;
    }

    class Schedule {

        List<Fixture> games;

        public Schedule() {
            this.games = new ArrayList<>();
        }

        @Override
        public String toString() {
            return games.toString();
        }
    }

    class Record {
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
