package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;
import com.foofinc.cfbra.api.jsondatastructures.Stats;
import com.foofinc.cfbra.api.jsondatastructures.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SchoolStringMapper {

    private static final String schoolDivider = "<>";
    private static final String fixtureDivider = "Fix";
    private static final String teamDivider = "Team";
    private static final String teamFieldDivider = "/";
    private static final String statsArrayDivider = "Stat";
    private static final String statDivider = "=";

    private SchoolStringMapper() {
    }

    /*
    String mapping of schools will be as followed (no spaces)-

        School.name | School.mascot | School.abbreviation |
            Fix
                Team team.name/team.points/ Stat stat.cat=stat.val Stat stat.cat=stat.val
                Team team.name/team.points/ Stat stat.cat=stat.val Stat stat.cat=stat.val
                    ]..

        */
    public static String mapSchoolEntryToString(Map.Entry<School, List<Fixture>> entry) {
        StringBuilder sb = new StringBuilder();
        School tempSchool = entry.getKey();

        //School
        sb.append(tempSchool.getSchool())
          .append(schoolDivider)
          .append(tempSchool.getMascot())
          .append(schoolDivider)
          .append(tempSchool.getAbbreviation())
          .append(schoolDivider);

        for (Fixture fix : entry.getValue()) {
            sb.append(fixtureDivider);
            for (Team team : fix.getTeams()) {
                sb.append(teamDivider);
                sb.append(team.getSchool()).append(teamFieldDivider);
                sb.append(team.getPoints()).append(teamFieldDivider);
                for (Stats stat : team.getStats()) {
                    sb.append(statsArrayDivider);
                    sb.append(stat.getCategory()).append(statDivider);
                    sb.append(stat.getStat());
                }
            }
        }
        return sb.toString();
    }

    public static Map.Entry<School, List<Fixture>> mapStringToSchoolEntry(String s) {

        String[] schoolFieldsAndFixturesString = s.split(schoolDivider);

        School school = getSchoolFromStrings(schoolFieldsAndFixturesString);

        List<Fixture> fixtures = new ArrayList<>();

        String[] fixturesString = schoolFieldsAndFixturesString[3].split(fixtureDivider);

        for (int i = 1; i < fixturesString.length; i++) {
            String fix = fixturesString[i];

            Fixture fixture = new Fixture();

            Team[] teams = getTeamsFromString(fix);

            fixture.setTeams(teams);
            fixtures.add(fixture);
        }
        return getMapEntry(school, fixtures);
    }

    private static School getSchoolFromStrings(String[] schoolFieldsAndRawFixtures) {
        return new School(schoolFieldsAndRawFixtures[0],
                          schoolFieldsAndRawFixtures[1],
                          schoolFieldsAndRawFixtures[2]);
    }

    private static Team[] getTeamsFromString(String fix) {
        String[] teamsString = fix.split(teamDivider);

        String team1String = teamsString[1];
        String team2String = teamsString[2];

        String[] team1Fields = team1String.split(teamFieldDivider);
        String[] team2Fields = team2String.split(teamFieldDivider);

        Team team1 = getTeamFromString(team1Fields);
        Team team2 = getTeamFromString(team2Fields);

        String[] team1StatsString = team1Fields[2].split(statsArrayDivider);
        String[] team2StatsString = team2Fields[2].split(statsArrayDivider);

        Stats[] team1Stats = new Stats[team1StatsString.length - 1];
        Stats[] team2Stats = new Stats[team2StatsString.length - 1];

        convertStringToStats(team1StatsString, team1Stats);
        convertStringToStats(team2StatsString, team2Stats);

        team1.setStats(team1Stats);
        team2.setStats(team2Stats);

        return new Team[]{team1, team2};
    }

    private static Team getTeamFromString(String[] team1Fields) {
        Team team1 = new Team();
        team1.setSchool(team1Fields[0]);
        team1.setPoints(Integer.parseInt(team1Fields[1]));
        return team1;
    }

    private static void convertStringToStats(String[] team1StatsString, Stats[] team1Stats) {
        for (int j = 1; j < team1StatsString.length; j++) {
            String[] stat = team1StatsString[j].split(statDivider);
            Stats tempStat = new Stats(stat[0], stat[1]);
            team1Stats[j - 1] = tempStat;
        }
    }

    private static Map.Entry<School, List<Fixture>> getMapEntry(School school, List<Fixture> fixtures) {
        return new Map.Entry<>() {
            @Override
            public School getKey() {
                return school;
            }

            @Override
            public List<Fixture> getValue() {
                return fixtures;
            }

            @Override
            public List<Fixture> setValue(List<Fixture> value) {
                return null;
            }
        };
    }
}
