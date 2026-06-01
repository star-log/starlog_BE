package com.starlog_be.fortune;

import java.util.Arrays;

public enum Star {
    ARIES("ARIES", "양자리", "おひつじ座", 3, 21, 4, 19),
    TAURUS("TAURUS", "황소자리", "おうし座", 4, 20, 5, 20),
    GEMINI("GEMINI", "쌍둥이자리", "ふたご座", 5, 21, 6, 21),
    CANCER("CANCER", "게자리", "かに座", 6, 22, 7, 22),
    LEO("LEO", "사자자리", "しし座", 7, 23, 8, 22),
    VIRGO("VIRGO", "처녀자리", "おとめ座", 8, 23, 9, 22),
    LIBRA("LIBRA", "천칭자리", "てんびん座", 9, 23, 10, 23),
    SCORPIO("SCORPIO", "전갈자리", "さそり座", 10, 24, 11, 22),
    SAGITTARIUS("SAGITTARIUS", "사수자리", "いて座", 11, 23, 12, 21),
    CAPRICORN("CAPRICORN", "염소자리", "やぎ座", 12, 22, 1, 19),
    AQUARIUS("AQUARIUS", "물병자리", "みずがめ座", 1, 20, 2, 18),
    PISCES("PISCES", "물고기자리", "うお座", 2, 19, 3, 20);

    private final String name;
    private final String nameKo;
    private final String nameJa;
    private final int startMonth;
    private final int startDate;
    private final int EndMonth;
    private final int EndDate;

    Star(String name, String nameKo, String nameJa, int startMonth, int startDate, int endMonth, int endDate) {
        this.name = name;
        this.nameKo = nameKo;
        this.nameJa = nameJa;
        this.startMonth = startMonth;
        this.startDate = startDate;
        EndMonth = endMonth;
        EndDate = endDate;
    }

    public static Star findStarByOriginalName(String nameJa) {
        return Arrays.stream(Star.values())
                .filter(star -> nameJa.contains(star.nameJa))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 별자리를 찾지 못했습니다. 그 별자리의 이름은 바로 " + nameJa));
    }

    public static String findStarNameByOriginalName(String nameJa) {
        return Arrays.stream(Star.values())
                .filter(star -> nameJa.contains(star.nameJa))
                .findAny()
                .toString();
    }
}
