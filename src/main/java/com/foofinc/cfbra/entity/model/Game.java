package com.foofinc.cfbra.entity.model;

import java.io.Serializable;

public class Game implements Serializable {

    private final School home;
    private final School away;

    private final int homeScore;
    private final int awayScore;

    private final int homeYardsGained;
    private final int awayYardsGained;

    private Game(Builder builder) {
        home = builder.home;
        away = builder.away;
        homeScore = builder.homeScore;
        awayScore = builder.awayScore;
        homeYardsGained = builder.homeYardsGained;
        awayYardsGained = builder.awayYardsGained;
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

    public static final class Builder {
        private School home;
        private School away;
        private int homeScore;
        private int awayScore;
        private int homeYardsGained;
        private int awayYardsGained;

        public Builder() {
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

        public Game build() {
            return new Game(this);
        }
    }
}
