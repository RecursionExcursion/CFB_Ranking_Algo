package com.foofinc.cfbra.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RankingAlgo {

    private final Map<StatisticizedTeam, Double> teamWeightMap;
    private List<StatisticizedTeam> rankedTeams;

    //Inner class
    private final RankingLists rankingLists = new RankingLists();

    //StatisticizedTeam method signatures
    private final String getWinsString = "getWins";
    private final String getPointsForString = "getPointsFor";
    private final String getPointsAllowedString = "getPointsAllowed";
    private final String getTotalOffenseString = "getTotalOffense";
    private final String getTotalDefenseString = "getTotalDefense";
    private final String getStrengthOfScheduleString = "getStrengthOfSchedule";


    public RankingAlgo(List<StatisticizedTeam> statTeam) {
        teamWeightMap = initializeTeamWeightMap(statTeam);
    }

    public Map<StatisticizedTeam, Double> initializeTeamWeightMap(List<StatisticizedTeam> statTeams) {
        Map<StatisticizedTeam, Double> teamDoubleMap = new HashMap<>();
        statTeams.forEach(t -> teamDoubleMap.put(t, 0.0));
        return teamDoubleMap;
    }

    public void rankTeams() {
        rankTeamsByCategory();
        getWeightedRankings();

        rankingLists.setTeamsRankedByTotalWeight(teamWeightMap.entrySet()
                                                              .stream()
                                                              .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                                                              .toList());


        rankedTeams = rankingLists.getTeamsRankedByTotalWeight()
                                  .stream()
                                  .map(Map.Entry::getKey)
                                  .toList();

        setRankings(rankedTeams);
    }

    private void rankTeamsByCategory() {
        rankingLists.setTeamsRankedByRecord(rankTeamsByMethodCalled(getWinsString));
        rankingLists.setTeamsRankedByPointsFor(rankTeamsByMethodCalled(getPointsForString));
        rankingLists.setTeamsRankedByPointsAllowed(rankTeamsByMethodCalled(getPointsAllowedString));
        rankingLists.setTeamsRankedByTotalOffense(rankTeamsByMethodCalled(getTotalOffenseString));
        rankingLists.setTeamsRankedByTotalDefense(rankTeamsByMethodCalled(getTotalDefenseString));
        rankingLists.setTeamsRankedByStrengthOfSchedule(rankTeamsByMethodCalled(getStrengthOfScheduleString));
    }

    private void getWeightedRankings() {
        getWeightOfRankings(rankingLists.getTeamsRankedByRecord(), getWinsString);
        getWeightOfRankings(rankingLists.getTeamsRankedByPointsFor(), getPointsForString);
        getWeightOfRankings(rankingLists.getTeamsRankedByPointsAllowed(), getPointsAllowedString);
        getWeightOfRankings(rankingLists.getTeamsRankedByTotalOffense(), getTotalOffenseString);
        getWeightOfRankings(rankingLists.getTeamsRankedByTotalDefense(), getTotalDefenseString);
        getWeightOfRankings(rankingLists.getTeamsRankedByStrengthOfSchedule(), getStrengthOfScheduleString);
        setWeight();
    }


    /*
    This method calculates the weight from all the smaller rankings and puts it into teamsAndWeightMap.
     */
    private void getWeightOfRankings(List<StatisticizedTeam> rankedTeams, String methodName) {

        int multiplier = Objects.equals(methodName, getWinsString) ? 10 : 1;
        multiplier = Objects.equals(methodName, getPointsForString) ? 5 : multiplier;
        multiplier = Objects.equals(methodName, getPointsAllowedString) ? 5 : multiplier;
        multiplier = Objects.equals(methodName, getStrengthOfScheduleString) ? 3 : multiplier;

        try {
            //Placeholder for previous team
            StatisticizedTeam lastTeam = null;

            for (int i = 0, rankWeight = 1; i < rankedTeams.size(); i++) {
                //Team getting weighted
                StatisticizedTeam indexedTeam = rankedTeams.get(i);

                //Skips first team being weighted since there's nothing to compare to.
                if (lastTeam != null) {

                    //Get method signature from String param
                    Method method = StatisticizedTeam.class.getMethod(methodName);

                    //Get stat of this team and previous team
                    int lastTeamValue = (int) method.invoke(lastTeam);
                    int indexedTeamValue = (int) method.invoke(indexedTeam);

                    //If they are different, move weight index to current list index
                    if (lastTeamValue != indexedTeamValue) {
                        rankWeight = i + 1;
                    }
                }
                teamWeightMap.put(indexedTeam, teamWeightMap.get(indexedTeam) + (rankWeight * multiplier));
                lastTeam = indexedTeam;
            }
        } catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    private List<StatisticizedTeam> rankTeamsByMethodCalled(String methodName) {

        return switch (methodName) {
            case getWinsString -> teamWeightMap.keySet()
                                               .stream()
                                               .sorted(Comparator.comparing(StatisticizedTeam::getWins).reversed())
                                               .toList();

            case getPointsForString -> teamWeightMap.keySet()
                                                    .stream()
                                                    .sorted(Comparator.comparingInt(StatisticizedTeam::getPointsFor)
                                                                      .reversed())
                                                    .toList();

            case getPointsAllowedString -> teamWeightMap.keySet()
                                                        .stream()
                                                        .sorted(Comparator.comparingInt(StatisticizedTeam::getPointsAllowed))
                                                        .toList();

            case getTotalOffenseString -> teamWeightMap.keySet()
                                                       .stream()
                                                       .sorted(Comparator.comparingInt(StatisticizedTeam::getTotalOffense)
                                                                         .reversed())
                                                       .toList();

            case getTotalDefenseString -> teamWeightMap.keySet()
                                                       .stream()
                                                       .sorted(Comparator.comparing(StatisticizedTeam::getTotalDefense))
                                                       .toList();

            case getStrengthOfScheduleString -> teamWeightMap.keySet()
                                                             .stream()
                                                             .sorted(Comparator.comparing(StatisticizedTeam::getStrengthOfSchedule)
                                                                               .reversed())
                                                             .toList();

            default -> throw new IllegalArgumentException("Method does not exist for object of type 'CompleteTeam'");
        };
    }

    private void setWeight() {
        teamWeightMap.forEach(StatisticizedTeam::setWeight);
    }

    private void setRankings(List<StatisticizedTeam> rankings) {
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRank(i + 1);
        }
    }

    @Override
    public String toString() {
        return rankedTeams.toString();
    }

    class RankingLists {

        private List<StatisticizedTeam> teamsRankedByRecord;
        private List<StatisticizedTeam> teamsRankedByPointsFor;
        private List<StatisticizedTeam> teamsRankedByPointsAllowed;
        private List<StatisticizedTeam> teamsRankedByTotalOffense;
        private List<StatisticizedTeam> teamsRankedByTotalDefense;
        private List<StatisticizedTeam> teamsRankedByStrengthOfSchedule;

        private List<Map.Entry<StatisticizedTeam, Double>> teamsRankedByTotalWeight;

        public List<StatisticizedTeam> getTeamsRankedByRecord() {
            return teamsRankedByRecord;
        }

        public void setTeamsRankedByRecord(List<StatisticizedTeam> teamsRankedByRecord) {
            this.teamsRankedByRecord = teamsRankedByRecord;
        }

        public List<StatisticizedTeam> getTeamsRankedByPointsFor() {
            return teamsRankedByPointsFor;
        }

        public void setTeamsRankedByPointsFor(List<StatisticizedTeam> teamsRankedByPointsFor) {
            this.teamsRankedByPointsFor = teamsRankedByPointsFor;
        }

        public List<StatisticizedTeam> getTeamsRankedByPointsAllowed() {
            return teamsRankedByPointsAllowed;
        }

        public void setTeamsRankedByPointsAllowed(List<StatisticizedTeam> teamsRankedByPointsAllowed) {
            this.teamsRankedByPointsAllowed = teamsRankedByPointsAllowed;
        }

        public List<StatisticizedTeam> getTeamsRankedByTotalOffense() {
            return teamsRankedByTotalOffense;
        }

        public void setTeamsRankedByTotalOffense(List<StatisticizedTeam> teamsRankedByTotalOffense) {
            this.teamsRankedByTotalOffense = teamsRankedByTotalOffense;
        }

        public List<StatisticizedTeam> getTeamsRankedByTotalDefense() {
            return teamsRankedByTotalDefense;
        }

        public void setTeamsRankedByTotalDefense(List<StatisticizedTeam> teamsRankedByTotalDefense) {
            this.teamsRankedByTotalDefense = teamsRankedByTotalDefense;
        }

        public List<StatisticizedTeam> getTeamsRankedByStrengthOfSchedule() {
            return teamsRankedByStrengthOfSchedule;
        }

        public void setTeamsRankedByStrengthOfSchedule(List<StatisticizedTeam> teamsRankedByStrengthOfSchedule) {
            this.teamsRankedByStrengthOfSchedule = teamsRankedByStrengthOfSchedule;
        }

        public List<Map.Entry<StatisticizedTeam, Double>> getTeamsRankedByTotalWeight() {
            return teamsRankedByTotalWeight;
        }

        public void setTeamsRankedByTotalWeight(List<Map.Entry<StatisticizedTeam, Double>> teamsRankedByTotalWeight) {
            this.teamsRankedByTotalWeight = teamsRankedByTotalWeight;
        }
    }
}
