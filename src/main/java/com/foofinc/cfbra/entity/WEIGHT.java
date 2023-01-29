package com.foofinc.cfbra.entity;

public enum WEIGHT {
    WINS(6),
    POINTS_FOR(4),
    POINTS_ALLOWED(4),
    STRENGTH_OF_SCHEDULE(2),
    OFFENSIVE_YARDS(1),
    DEFENSIVE_YARDS(1),
    POLL_INERTIA(5);

    private final int val;

    WEIGHT(int i) {
        val = i;
    }

    public int getVal() {
        return val;
    }
}
