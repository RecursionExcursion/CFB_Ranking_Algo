package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.json.jsondatastructures.Fixture;
import com.foofinc.cfbra.json.jsondatastructures.School;
import com.foofinc.cfbra.json.jsondatastructures.Stats;
import com.foofinc.cfbra.json.jsondatastructures.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryManager {

    private String filePath = "src/main/java/com/foofinc/cfbra/persistence/fbs_data";
    private List<String> dataIn;
    private List<String> dataOut;


    public MemoryManager() {
        this.dataIn = new ArrayList<>();
        this.dataOut = new ArrayList<>();
    }

    //For Testing
    public MemoryManager(String filePath) {
        this();
        this.filePath = filePath;
    }

    public Map<School, List<Fixture>> loadSchools() {
        Map<School, List<Fixture>> map = new HashMap<>();
        Map.Entry<School, List<Fixture>> entry;

        dataIn = FileScribe.readFromFile(filePath);
        for (String s : dataIn) {
            entry = mapStringToSchoolEntry(s);
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public void saveSchools(Map<School, List<Fixture>> schools) {
        for (Map.Entry<School, List<Fixture>> entry : schools.entrySet()) {
            dataOut.add(mapSchoolMapToString(entry));
        }
        FileScribe.writeToFile(filePath, dataOut);
    }
        //TODO Refactor


    /*
    String mapping of schools will be as followed-
            'School.name|School.mascot|School.abbreviation|Fixture[Team[stats]Team[stats]]
     */
    private String mapSchoolMapToString(Map.Entry<School, List<Fixture>> entry) {
        String base = entry.toString();
        StringBuilder sb = new StringBuilder();
        String divider = "<>";
        String divider2 = "/";
        String divider3 = "~";
        School tempSchool = entry.getKey();

        //School
        sb.append(tempSchool.getSchool())
          .append(divider)
          .append(tempSchool.getMascot())
          .append(divider)
          .append(tempSchool.getAbbreviation())
          .append(divider);

        for (Fixture fix : entry.getValue()) {
            sb.append("Fix");
            for (Team team : fix.getTeams()) {
                sb.append("Team");
                sb.append(team.getSchool()).append(divider2);
                sb.append(team.getPoints()).append(divider2);
                for (Stats stat : team.getStats()) {
                    sb.append("Stat");
                    sb.append(stat.getCategory()).append("=");
                    sb.append(stat.getStat());
                }
            }

        }

        return sb.toString();
    }

    private Map.Entry<School, List<Fixture>> mapStringToSchoolEntry(String s) {

        Map<School, List<Fixture>> map = new HashMap<>();

        String[] schoolFieldsAndRawFixtures = s.split("<>" .toString());
        School school = new School(schoolFieldsAndRawFixtures[0],
                                   schoolFieldsAndRawFixtures[1],
                                   schoolFieldsAndRawFixtures[2]);
        List<Fixture> fixtures = new ArrayList<>();


        String[] fixturesString = schoolFieldsAndRawFixtures[3].split("Fix");

        for (int i = 1; i < fixturesString.length; i++) {
            String fix = fixturesString[i];

            Fixture fixture = new Fixture();

            String[] teamsString = fix.split("Team");

            String team1String = teamsString[1];
            String team2String = teamsString[2];

            String[] team1Fields = team1String.split("/");
            String[] team2Fields = team2String.split("/");

            Team team1 = new Team();
            team1.setSchool(team1Fields[0]);
            team1.setPoints(Integer.parseInt(team1Fields[1]));

            Team team2 = new Team();
            team2.setSchool(team2Fields[0]);
            team2.setPoints(Integer.parseInt(team2Fields[1]));

            String[] team1StatsString = team1Fields[2].split("Stat");
            String[] team2StatsString = team2Fields[2].split("Stat");

            Stats[] team1Stats = new Stats[team1StatsString.length - 1];
            Stats[] team2Stats = new Stats[team2StatsString.length - 1];

            for (int j = 1; j < team1StatsString.length; j++) {
                String[] stat = team1StatsString[j].split("=");
                Stats tempStat = new Stats(stat[0], stat[1]);
                team1Stats[j - 1] = tempStat;
            }
            for (int j = 2; j < team2StatsString.length; j++) {
                String[] stat = team2StatsString[j].split("=");
                Stats tempStat = new Stats(stat[0], stat[1]);
                team2Stats[j - 1] = tempStat;
            }

            team1.setStats(team1Stats);
            team2.setStats(team2Stats);

            Team[] teams = {team1, team2};

            fixture.setTeams(teams);
            fixtures.add(fixture);
        }
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
