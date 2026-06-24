package com.starlog_be.fortune.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Fortune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Star star;

    private int starRank;

    private String description;
    private String luckyColor;
    private String luckyKey;

    private int moneyLuckyScore;
    private int loveLuckyScore;
    private int workLuckyScore;
    private int healthLuckyScore;

    protected Fortune() {}

    public Fortune(LocalDate today, Star star, int starRank, String description, String luckyColor, String luckyKey, int moneyLuckyScore, int loveLuckyScore, int workLuckyScore, int healthLuckyScore) {
        this.date = today;
        this.star = star;
        this.starRank = starRank;

        this.description = description;
        this.luckyColor = luckyColor;
        this.luckyKey = luckyKey;
        this.moneyLuckyScore = moneyLuckyScore;
        this.loveLuckyScore = loveLuckyScore;
        this.workLuckyScore = workLuckyScore;
        this.healthLuckyScore = healthLuckyScore;
    }
}
