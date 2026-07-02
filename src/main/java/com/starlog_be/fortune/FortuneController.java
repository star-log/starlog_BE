package com.starlog_be.fortune;

import com.starlog_be.fortune.dto.QueryFortuneDetailResponse;
import com.starlog_be.fortune.dto.QueryFortunesResponse;
import com.starlog_be.fortune.service.QueryFortuneDetailService;
import com.starlog_be.fortune.service.QueryFortunesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FortuneController {
    private final QueryFortunesService queryFortunesService;
    private final QueryFortuneDetailService queryFortuneDetailService;

    @GetMapping
    public List<QueryFortunesResponse> queryFortunes() {
        return queryFortunesService.execute();
    }

    @GetMapping("/{id}")
    public QueryFortuneDetailResponse queryFortuneDetail() {
        return queryFortuneDetailService.execute();
    }
}
