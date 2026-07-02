package com.starlog_be.fortune.dto;

import com.starlog_be.fortune.domain.Star;

public record QueryFortuneDetailResponse(
        Star star,
        int starRank,
        String description,
        String luckyColor,
        String luckyKey,
        int moneyLuckyScore,
        int loveLuckyScore,
        int workLuckyScore,
        int healthLuckyScore
) {
}
