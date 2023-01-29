package com.foofinc.cfbra.entity.model;

import java.io.Serializable;

public class Game implements Serializable {

    private final long id;

    private final School home;
    private final School away;

    private final int homeScore;
    private final int awayScore;

    private final int homeYardsGained;
    private final int awayYardsGained;

    private final int season;
    private final int week;
    private final String season_type;
    private final String start_date;
    private final boolean completed;
    private final int home_id;
    private final int away_id;

    private Game(Builder builder) {
        id = builder.id;
        home = builder.home;
        away = builder.away;
        homeScore = builder.homeScore;
        awayScore = builder.awayScore;
        homeYardsGained = builder.homeYardsGained;
        awayYardsGained = builder.awayYardsGained;
        season = builder.season;
        week = builder.week;
        season_type = builder.season_type;
        start_date = builder.start_date;
        completed = builder.completed;
        home_id = builder.home_id;
        away_id = builder.away_id;
    }

    public School getHome() {
        return home;
    }

    public School getAway() {
        return away;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getHomeYardsGained() {
        return homeYardsGained;
    }

    public int getAwayYardsGained() {
        return awayYardsGained;
    }

    public long getId() {
        return id;
    }

    public int getSeason() {
        return season;
    }

    public int getWeek() {
        return week;
    }

    public String getSeason_type() {
        return season_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getHome_id() {
        return home_id;
    }

    public int getAway_id() {
        return away_id;
    }


    public static final class Builder {
        private long id;

        private School home;
        private School away;
        private int homeScore;
        private int awayScore;
        private int homeYardsGained;
        private int awayYardsGained;

        private int season;
        private int week;
        private String season_type;
        private String start_date;
        private boolean completed;
        private int home_id;
        private int away_id;

        public Builder() {
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withHome(School home) {
            this.home = home;
            return this;
        }

        public Builder withAway(School away) {
            this.away = away;
            return this;
        }

        public Builder withHomeScore(int homeScore) {
            this.homeScore = homeScore;
            return this;
        }

        public Builder withAwayScore(int awayScore) {
            this.awayScore = awayScore;
            return this;
        }

        public Builder withHomeYardsGained(int homeYardsGained) {
            this.homeYardsGained = homeYardsGained;
            return this;
        }

        public Builder withAwayYardsGained(int awayYardsGained) {
            this.awayYardsGained = awayYardsGained;
            return this;
        }

        public Builder withSeason(int season) {
            this.season = season;
            return this;
        }

        public Builder withWeek(int week) {
            this.week = week;
            return this;
        }

        public Builder withSeason_type(String season_type) {
            this.season_type = season_type;
            return this;
        }

        public Builder withStart_date(String start_date) {
            this.start_date = start_date;
            return this;
        }

        public Builder withCompleted(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder withHome_id(int home_id) {
            this.home_id = home_id;
            return this;
        }

        public Builder withAway_id(int away_id) {
            this.away_id = away_id;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
