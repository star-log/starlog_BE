package com.starlog_be.fortune;

public record FortuneRaw(
        String starName,
        Integer rank,
        String description,
        String luckyColor,
        String luckyKey,
        Integer moneyScore,
        Integer loveScore,
        Integer workScore,
        Integer healthScore
) {
}
