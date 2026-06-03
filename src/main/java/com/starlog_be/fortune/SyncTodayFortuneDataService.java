package com.starlog_be.fortune;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.starlog_be.TranslationService.translateJaTextsToKoTexts;

@Service
@RequiredArgsConstructor
public class SyncTodayFortuneDataService {
    private final FortuneRepository fortuneRepository;
    private final FortuneConfig fortuneConfig;

    @Transactional
    public void execute() {
        try {
            Document doc = Jsoup.connect(fortuneConfig.fortuneUrl())
                    .userAgent(fortuneConfig.userAgent())
                    .timeout(fortuneConfig.timeout())
                    .get();
            FortuneRaw fortuneRaw = FortuneRaw.of(doc, LocalDateTime.now());

            List<String> translateSource = fortuneRaw.getTranslateSource();
            List<String> koTexts = translateJaTextsToKoTexts(translateSource);

            saveFortunes(fortuneRaw, koTexts);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFortunes(FortuneRaw fortuneRaw, List<String> koTexts) {
        int koTextsIndex = 0;
        List<Fortune> fortuneList = new ArrayList<>();
        for (Element starDetail : fortuneRaw.starDetails()) {
            String starNameJa = starDetail.selectFirst(".seiza-txt").ownText().trim();

            Fortune fortune = new Fortune(
                    fortuneRaw.today().toLocalDate(),
                    Star.findStarByOriginalName(starNameJa),
                    fortuneRaw.rankMap().get(starNameJa),
                    koTexts.get(koTextsIndex++),
                    koTexts.get(koTextsIndex++),
                    koTexts.get(koTextsIndex++),
                    starDetail.select(".icon-money").size(),
                    starDetail.select(".icon-love").size(),
                    starDetail.select(".icon-work").size(),
                    starDetail.select(".icon-health").size()
            );
            fortuneList.add(fortune);
        }
        fortuneRepository.saveAll(fortuneList);
    }
}
