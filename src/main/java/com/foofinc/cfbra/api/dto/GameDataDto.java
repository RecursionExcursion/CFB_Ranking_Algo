package com.foofinc.cfbra.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDataDto implements DTO {

    private long id;
    private int season;
    private int week;
    private String season_type;
    private String start_date;
    private boolean completed;
    private int home_id;
    private int away_id;

    public GameDataDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getSeason_type() {
        return season_type;
    }

    public void setSeason_type(String season_type) {
        this.season_type = season_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getHome_id() {
        return home_id;
    }

    public void setHome_id(int home_id) {
        this.home_id = home_id;
    }

    public int getAway_id() {
        return away_id;
    }

    public void setAway_id(int away_id) {
        this.away_id = away_id;
    }

    @Override
    public String toString() {
        return "GameDataDto{" +
                "id=" + id +
                ", season=" + season +
                ", week=" + week +
                ", season_type='" + season_type + '\'' +
                ", start_date='" + start_date + '\'' +
                ", completed=" + completed +
                ", home_id=" + home_id +
                ", away_id=" + away_id +
                '}';
    }
}
