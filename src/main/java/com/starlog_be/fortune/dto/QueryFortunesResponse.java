package com.starlog_be.fortune.dto;

import com.starlog_be.fortune.domain.Star;

public record QueryFortunesResponse(
        Long id,
        Star star,
        int starRank
) {
}
