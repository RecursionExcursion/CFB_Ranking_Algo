package com.foofinc.cfbra.entity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class School implements Serializable {
    private final String schoolName;
    private final String mascot;
    private final String abbreviation;
    private final List<Game> schedule;

    public School(String school, String mascot, String abbreviation) {
        this.schoolName = school;
        this.mascot = mascot;
        this.abbreviation = abbreviation;
        schedule = new ArrayList<>();
    }

    private School(Builder builder) {
        schoolName = builder.school;
        mascot = builder.mascot;
        abbreviation = builder.abbreviation;
        schedule = new ArrayList<>();
    }

    public void addGameToSchedule(Game game) {
        schedule.add(game);
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
        return List.copyOf(schedule);
    }

    public static final class Builder {
        private String school;
        private String mascot;
        private String abbreviation;

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

        public School build() {
            return new School(this);
        }
    }
}

