package com.starlog_be.fortune.service;

import com.starlog_be.fortune.FortuneRepository;
import com.starlog_be.fortune.domain.Fortune;
import com.starlog_be.fortune.dto.QueryFortuneDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryFortuneDetailService {
    private final FortuneRepository fortuneRepository;

    public QueryFortuneDetailResponse execute(Long id) {
        Fortune fortune = fortuneRepository.findByid(id);
        return new QueryFortuneDetailResponse(
                fortune.getStar(),
                fortune.getStarRank(),
                fortune.getDescription(),
                fortune.getLuckyColor(),
                fortune.getLuckyKey(),
                fortune.getMoneyLuckyScore(),
                fortune.getLoveLuckyScore(),
                fortune.getWorkLuckyScore(),
                fortune.getHealthLuckyScore()
        );
    }
}
