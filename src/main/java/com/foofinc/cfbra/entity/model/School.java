package com.foofinc.cfbra.entity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class School implements Serializable {
    private String schoolName;
    private String mascot;
    private String abbreviation;
    private Schedule schedule;

    public School(String school, String mascot, String abbreviation) {
        this.schoolName = school;
        this.mascot = mascot;
        this.abbreviation = abbreviation;
        schedule = new Schedule();
    }

    private School(Builder builder) {
        schoolName = builder.school;
        mascot = builder.mascot;
        abbreviation = builder.abbreviation;
        schedule = builder.schedule == null ? new Schedule() : builder.schedule;
    }

    public void addGameToSchedule(Game game) {
        schedule.addGame(game);
    }

    public String getSchoolNameString() {
        return schoolName;
    }

    public String getMascot() {
        return mascot;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public List<Game> getSchedule() {
        return schedule.getGames();
    }

    class Schedule implements Serializable {

        private final List<Game> games;

        public Schedule() {
            this.games = new ArrayList<>();
        }

        public void addGame(Game game) {
            games.add(game);
        }

        public List<Game> getGames() {
            return games;
        }

        @Override
        public String toString() {
            return games.toString();
        }
    }

    public static final class Builder {
        private String school;
        private String mascot;
        private String abbreviation;
        private Schedule schedule;

        public Builder() {
        }

        public Builder withSchool(String school) {
            this.school = school;
            return this;
        }

        public Builder withMascot(String mascot) {
            this.mascot = mascot;
            return this;
        }

        public Builder withAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
            return this;
        }

        public Builder withSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public School build() {
            return new School(this);
        }
    }
}

