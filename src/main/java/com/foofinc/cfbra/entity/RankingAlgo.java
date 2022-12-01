package com.foofinc.cfbra.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RankingAlgo {

    private final List<CompleteTeam> teams;
    private final Map<CompleteTeam, Double> teamRankWeight;
    private List<CompleteTeam> rankedTeams;


    private List<CompleteTeam> teamsRankedByRecord;
    private List<CompleteTeam> teamsRankedByPointsFor;
    private List<CompleteTeam> teamsRankedByPointsAllowed;
    private List<CompleteTeam> teamsRankedByTotalOffense;
    private List<CompleteTeam> teamsRankedByTotalDefense;
    private List<CompleteTeam> teamsRankedByStrengthOfSchedule;

    private List<Map.Entry<CompleteTeam, Double>> teamsRankedByTotalWeight;


    public RankingAlgo() {
        teams = new ArrayList<>();
        teamRankWeight = new HashMap<>();
    }


    public void addTeam(CompleteTeam team) {
        teams.add(team);
        teamRankWeight.put(team, 0.0);
    }

    public void rankTeams() {
        rankTeamsByCategory();
        getWeightedRankings();

        teamsRankedByTotalWeight = teamRankWeight.entrySet().stream()
                                                 .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                                                 .toList();


        rankedTeams = teamsRankedByTotalWeight.stream()
                                              .map(Map.Entry::getKey)
                                              .toList();

        setRankings(rankedTeams);
    }

    private void rankTeamsByCategory() {
        teamsRankedByRecord = rankTeamsByMethodCalled("getWins");
        teamsRankedByPointsFor = rankTeamsByMethodCalled("getPointsFor");
        teamsRankedByPointsAllowed = rankTeamsByMethodCalled("getPointsAllowed");
        teamsRankedByTotalOffense = rankTeamsByMethodCalled("getTotalOffense");
        teamsRankedByTotalDefense = rankTeamsByMethodCalled("getTotalDefense");
        teamsRankedByStrengthOfSchedule = rankTeamsByMethodCalled("getStrengthOfSchedule");
    }

    private void getWeightedRankings() {
        getWeightOfRankings(teamsRankedByRecord, "getWins");
        getWeightOfRankings(teamsRankedByPointsFor, "getPointsFor");
        getWeightOfRankings(teamsRankedByPointsAllowed, "getPointsAllowed");
        getWeightOfRankings(teamsRankedByTotalOffense, "getTotalOffense");
        getWeightOfRankings(teamsRankedByTotalDefense, "getTotalDefense");
        getWeightOfRankings(teamsRankedByStrengthOfSchedule, "getStrengthOfSchedule");
        setWeight();
    }

    private void getWeightOfRankings(List<CompleteTeam> rankedTeams, String methodName) {

        try {
            CompleteTeam lastTeam = null;
            for (int i = 0, rank = 1; i < rankedTeams.size(); i++) {
                CompleteTeam indexedTeam = rankedTeams.get(i);
                if (lastTeam != null) {

                    Class<?> c = Class.forName(CompleteTeam.class.getName());
                    Method method = c.getMethod(methodName);

                    int lastTeamValue = (int) method.invoke(lastTeam);
                    int indexedTeamValue = (int) method.invoke(indexedTeam);

                    if (lastTeamValue != indexedTeamValue) {
                        rank = i + 1;
                    }
                }
                teamRankWeight.put(indexedTeam, teamRankWeight.get(indexedTeam) + rank);
                lastTeam = indexedTeam;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | SecurityException |
                 IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CompleteTeam> rankTeamsByMethodCalled(String methodName) {

        return switch (methodName) {
            case "getWins" -> teams.stream()
                                   .sorted(Comparator.comparing(CompleteTeam::getWins).reversed()).toList();

            case "getPointsFor" -> teams.stream()
                                        .sorted(Comparator.comparingInt(CompleteTeam::getPointsFor).reversed())
                                        .toList();

            case "getPointsAllowed" -> teams.stream()
                                            .sorted(Comparator.comparingInt(CompleteTeam::getPointsAllowed)).toList();

            case "getTotalOffense" -> teams.stream()
                                           .sorted(Comparator.comparingInt(CompleteTeam::getTotalOffense).reversed())
                                           .toList();

            case "getTotalDefense" -> teams.stream()
                                           .sorted(Comparator.comparing(CompleteTeam::getTotalDefense)).toList();

            case "getStrengthOfSchedule" -> teams.stream()
                                                 .sorted(Comparator.comparing(CompleteTeam::getStrengthOfSchedule)
                                                                   .reversed()).toList();

            default -> throw new IllegalArgumentException("Method does not exist for object of type 'CompleteTeam'");
        };
    }

    private void setWeight() {
        teamRankWeight.forEach((key, value) -> key.setWeight(value));
    }

    private void setRankings(List<CompleteTeam> rankings) {
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRank(i + 1);
        }
    }

    @Override
    public String toString() {
        return rankedTeams.toString();
    }

}
