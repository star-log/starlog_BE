package com.starlog_be.fortune;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Fortune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Star star;

    private int rank;

    private String descriptionJa;
    private String luckyColorJa;
    private String luckyKeyJa;

    private String descriptionKo;
    private String luckyColorKo;
    private String luckyKeyKo;

    private int moneyLuckyScore;
    private int loveLuckyScore;
    private int workLuckyScore;
    private int healthLuckyScore;

    protected Fortune() {}

    public Fortune(LocalDate today, Star star, int rank, String descriptionJa, String luckyColorJa, String luckyKeyJa, String descriptionKo, String luckyColorKo, String luckyKeyKo, int moneyLuckyScore, int loveLuckyScore, int workLuckyScore, int healthLuckyScore) {
        this.date = today;
        this.star = star;
        this.rank = rank;
        this.descriptionJa = descriptionJa;
        this.luckyColorJa = luckyColorJa;
        this.luckyKeyJa = luckyKeyJa;
        this.descriptionKo = descriptionKo;
        this.luckyColorKo = luckyColorKo;
        this.luckyKeyJa = luckyKeyKo;
        this.moneyLuckyScore = moneyLuckyScore;
        this.loveLuckyScore = loveLuckyScore;
        this.workLuckyScore = workLuckyScore;
        this.healthLuckyScore = healthLuckyScore;
    }
}
