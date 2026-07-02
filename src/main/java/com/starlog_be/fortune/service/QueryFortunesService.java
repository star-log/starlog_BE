package com.starlog_be.fortune.service;

import com.starlog_be.fortune.FortuneRepository;
import com.starlog_be.fortune.domain.Fortune;
import com.starlog_be.fortune.dto.QueryFortunesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFortunesService {
    private final FortuneRepository fortuneRepository;

    public List<QueryFortunesResponse> execute() {
        List<Fortune> fortunes = fortuneRepository.findByDate(LocalDate.now());
        return fortunes.stream().map(fortune -> new QueryFortunesResponse(
            fortune.getId(),
            fortune.getStar(),
            fortune.getStarRank()
        )).toList();
    }
}
