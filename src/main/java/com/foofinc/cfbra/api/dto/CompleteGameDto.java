package com.foofinc.cfbra.api.dto;

/*
This class is a Data Structure used to hold data of multiple Jackson's objectmapper object.
POJO to hold games from weekly-games and team-games JSON
This class will be used to map this data to 'Game objects
 */
public class CompleteGameDto implements DTO{
    private long id;
    private FixtureDto fixtureDto;
    private GameDataDto gameDataDto;

    public CompleteGameDto(long id, FixtureDto fixtureDto) {
        this.id = id;
        this.fixtureDto = fixtureDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FixtureDto getFixtureDto() {
        return fixtureDto;
    }

    public void setFixtureDto(FixtureDto fixtureDto) {
        this.fixtureDto = fixtureDto;
    }

    public GameDataDto getGameDataDto() {
        return gameDataDto;
    }

    public void setGameDataDto(GameDataDto gameDataDto) {
        this.gameDataDto = gameDataDto;
    }

    /*
    Will return true if neither team is in FBS
     */
    public boolean hasNullData(){
        return fixtureDto == null || gameDataDto == null;
    }
}
