package com.starlog_be.fortune;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FortuneController {
    private final SyncTodayFortuneDataService syncTodayFortuneDataService;

    @PostMapping
    public void syncTodayFortuneData() {
        syncTodayFortuneDataService.execute();
    }
}
