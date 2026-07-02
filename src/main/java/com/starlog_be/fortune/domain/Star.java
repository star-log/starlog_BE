package com.starlog_be.fortune.domain;

public enum Star {
    ARIES("양자리"),
    TAURUS("황소자리"),
    GEMINI("쌍둥이자리"),
    CANCER("게자리"),
    LEO("사자자리"),
    VIRGO("처녀자리"),
    LIBRA("천칭자리"),
    SCORPIO("전갈자리"),
    SAGITTARIUS("사수자리"),
    CAPRICORN("염소자리"),
    AQUARIUS("물병자리"),
    PISCES("물고기자리"),
    DEFAULT("기본값");

    private final String name;

    Star(String name) {
        this.name = name;
    }

    public static Star findByName(String name) {
        for (Star star : Star.values()) {
            if (name.equals(star.name)) return star;
        }
        return DEFAULT;
    }
}
