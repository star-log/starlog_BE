package com.starlog_be.fortune.domain;

public enum Star {
    ARIES("양자리", 3, 21, 4, 19),
    TAURUS("황소자리", 4, 20, 5, 20),
    GEMINI("쌍둥이자리", 5, 21, 6, 21),
    CANCER("게자리", 6, 22, 7, 22),
    LEO("사자자리", 7, 23, 8, 22),
    VIRGO("처녀자리", 8, 23, 9, 22),
    LIBRA("천칭자리", 9, 23, 10, 23),
    SCORPIO("전갈자리", 10, 24, 11, 22),
    SAGITTARIUS("사수자리", 11, 23, 12, 21),
    CAPRICORN("염소자리", 12, 22, 1, 19),
    AQUARIUS("물병자리", 1, 20, 2, 18),
    PISCES("물고기자리", 2, 19, 3, 20),
    DEFAULT("기본값", 1, 1, 1, 1);

    private final String name;
    private final int startMonth;
    private final int startDate;
    private final int EndMonth;
    private final int EndDate;

    Star(String name, int startMonth, int startDate, int endMonth, int endDate) {
        this.name = name;
        this.startMonth = startMonth;
        this.startDate = startDate;
        EndMonth = endMonth;
        EndDate = endDate;
    }

    public static Star findByName(String name) {
        for (Star star : Star.values()) {
            if (name.equals(star.name)) return star;
        }
        return DEFAULT;
    }
}
