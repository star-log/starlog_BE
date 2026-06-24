package com.starlog_be.fortune;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FortuneScheduler {
    private final SyncTodayFortuneDataService syncTodayFortuneDataService;

    @Scheduled(cron = "0 0 6 * * *")
    public void syncTodayFortuneDataService() {
        syncTodayFortuneDataService.execute();
    }
}
