package com.foofinc.cfbra.entity;

import java.util.*;

public class RankingAlgo {

    private List<CompleteTeam> teams;
    private Map<CompleteTeam, Integer> teamRankWeight;
    private List<CompleteTeam> rankedTeams;

    public RankingAlgo() {
        teams = new ArrayList<>();
        teamRankWeight = new HashMap<>();
    }

    public void addTeam(CompleteTeam team) {
        teams.add(team);
        teamRankWeight.put(team, 0);
    }

    public void rankTeams() {
        rankTeamsByRecord();
        rankTeamsByPointsFor();
        rankTeamsPointsAllowed();
        rankTeamsByOffense();
        rankTeamsByDefense();


        rankedTeams = teamRankWeight.entrySet().stream()
                                    .sorted(Comparator.comparingInt(Map.Entry::getValue))
                                    .map(Map.Entry::getKey)
                                    .toList();

        setRankings(rankedTeams);
    }


    private void setRankings(List<CompleteTeam> rankings) {
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRank(i + 1);
        }
    }

    private void rankTeamsByRecord() {
        List<CompleteTeam> sortedTeams = teams.stream()
                                              .sorted(Comparator.comparing(CompleteTeam::getWins).reversed())
                                              .toList();
        CompleteTeam lastTeam = null;
        for (int i = 0, rank = 1; i < sortedTeams.size(); i++) {
            CompleteTeam indexedTeam = sortedTeams.get(i);
            if (lastTeam != null) {
                if (lastTeam.getWins() != indexedTeam.getWins()) {
                    rank = i + 1;
                }
            }
            teamRankWeight.put(indexedTeam, teamRankWeight.get(indexedTeam) + rank);
            lastTeam = indexedTeam;
        }
    }

    private void rankTeamsByPointsFor() {
        List<CompleteTeam> sortedTeams = teams.stream()
                                              .sorted(Comparator.comparingInt(CompleteTeam::getPointsFor).reversed())
                                              .toList();
        CompleteTeam lastTeam = null;
        for (int i = 0, rank = 1; i < sortedTeams.size(); i++) {
            CompleteTeam indexedTeam = sortedTeams.get(i);
            if (lastTeam != null) {
                if (lastTeam.getPointsFor() != indexedTeam.getPointsFor()) {
                    rank = i + 1;
                }
            }
            teamRankWeight.put(indexedTeam, teamRankWeight.get(indexedTeam) + rank);
            lastTeam = indexedTeam;
        }
    }

    private void rankTeamsPointsAllowed() {
        List<CompleteTeam> sortedTeams = teams.stream()
                                              .sorted(Comparator.comparingInt(CompleteTeam::getPointsAllowed))
                                              .toList();
        CompleteTeam lastTeam = null;
        for (int i = 0, rank = 1; i < sortedTeams.size(); i++) {
            CompleteTeam indexedTeam = sortedTeams.get(i);
            if (lastTeam != null) {
                if (lastTeam.getPointsAllowed() != indexedTeam.getPointsAllowed()) {
                    rank = i + 1;
                }
            }
            teamRankWeight.put(indexedTeam, teamRankWeight.get(indexedTeam) + rank);
            lastTeam = indexedTeam;
        }
    }

    private void rankTeamsByOffense() {
        List<CompleteTeam> sortedTeams = teams.stream()
                                              .sorted(Comparator.comparingInt(CompleteTeam::getTotalOffense).reversed())
                                              .toList();
        CompleteTeam lastTeam = null;
        for (int i = 0, rank = 1; i < sortedTeams.size(); i++) {
            CompleteTeam indexedTeam = sortedTeams.get(i);
            if (lastTeam != null) {
                if (lastTeam.getTotalOffense() != indexedTeam.getTotalOffense()) {
                    rank = i + 1;
                }
            }
            teamRankWeight.put(indexedTeam, teamRankWeight.get(indexedTeam) + rank);
            lastTeam = indexedTeam;
        }
    }

    private void rankTeamsByDefense() {
        List<CompleteTeam> sortedTeams = teams.stream()
                                              .sorted(Comparator.comparing(CompleteTeam::getTotalDefense))
                                              .toList();
        CompleteTeam lastTeam = null;
        for (int i = 0, rank = 1; i < sortedTeams.size(); i++) {
            CompleteTeam indexedTeam = sortedTeams.get(i);
            if (lastTeam != null) {
                if (lastTeam.getTotalDefense() != indexedTeam.getTotalDefense()) {
                    rank = i + 1;
                }
            }
            teamRankWeight.put(indexedTeam, teamRankWeight.get(indexedTeam) + rank);
            lastTeam = indexedTeam;
        }
    }

    @Override
    public String toString() {
        return rankedTeams.toString();
    }

}
