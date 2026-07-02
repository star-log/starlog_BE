package com.starlog_be.fortune;

import com.starlog_be.fortune.dto.QueryFortunesResponse;
import com.starlog_be.fortune.service.QueryFortunesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FortuneController {
    private final QueryFortunesService queryFortunesService;

    @GetMapping
    public List<QueryFortunesResponse> queryFortunes() {
        return queryFortunesService.execute();
    }
}
