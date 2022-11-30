package com.foofinc.cfbra.entity;

import com.foofinc.cfbra.json.jsondatastructures.Fixture;

import java.util.ArrayList;
import java.util.List;

public class CompleteTeam {

    private String name;
    private int rank;
    private Record record;

    private Schedule schedule;

    private int totalOffense;
    private int totalDefense;

    private int pointsAllowed;
    private int pointsFor;

    //TODO Add conference stats????

    public CompleteTeam(String name) {
        this.name = name;
        rank = 0;
        record = new Record();
        schedule = new Schedule();
        totalOffense = 0;
        totalDefense = 0;
        pointsAllowed = 0;
        pointsFor = 0;
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

    @Override
    public String toString() {
        return "\n#" + rank + " " + name +
                " " + record +
                " PF-" + pointsFor +
                " PA-" + pointsAllowed +
                " Offense-" + totalOffense +
                " Defense-" + totalDefense;
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
