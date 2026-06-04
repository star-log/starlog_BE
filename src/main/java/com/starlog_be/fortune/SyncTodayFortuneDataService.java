package com.starlog_be.fortune;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SyncTodayFortuneDataService {
    private final FortuneRepository fortuneRepository;

    @Transactional
    public void execute() {

    }
}
