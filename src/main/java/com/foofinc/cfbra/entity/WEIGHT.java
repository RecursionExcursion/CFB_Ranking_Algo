package com.foofinc.cfbra.entity;

public enum WEIGHT {
    WINS(10),
    POINTS_FOR(2),
    POINTS_ALLOWED(2),
    STRENGTH_OF_SCHEDULE(3),
    OFFENSIVE_YARDS(1),
    DEFENSIVE_YARDS(1);

    private final int val;

    WEIGHT(int i) {
        val = i;
    }

    public int getVal() {
        return val;
    }
}
