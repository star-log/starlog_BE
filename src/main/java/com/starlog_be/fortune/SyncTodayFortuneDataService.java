package com.starlog_be.fortune;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//            Map<String, Integer> rankMap = getRankMap(doc);
//
//            Elements starDetails = doc.select(".seiza-area > .seiza-box");
//            List<String> translateSource = getTranslateSource(starDetails);
            List<String> koTexts = translateJaTextsToKoTexts(translateSource);

            saveFortunes(starDetails, today, rankMap, koTexts);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFortunes(Elements starDetails, LocalDateTime today, Map<String, Integer> rankMap, List<String> koTexts) {
        int koTextsIndex = 0;
        List<Fortune> fortuneList = new ArrayList<>();
        for (Element starDetail : starDetails) {
            String starNameJa = starDetail.selectFirst(".seiza-txt").ownText().trim();

            Fortune fortune = new Fortune(
                    today.toLocalDate(),
                    Star.findStarByOriginalName(starNameJa),
                    rankMap.get(starNameJa),
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

    private List<String> getTranslateSource(Elements starDetails) {
        List<String> translateSource = new ArrayList<>();
        for (Element starDetail : starDetails) {
            String descriptionJa = starDetail.select(".read").text();
            String luckyColorJa = starDetail.selectFirst(".lucky-color-txt").nextSibling().toString().replace(":", "").trim();
            String luckyKeyJa = starDetail.selectFirst(".key-txt").nextSibling().toString().replace(":", "").trim();

            translateSource.add(descriptionJa);
            translateSource.add(luckyColorJa);
            translateSource.add(luckyKeyJa);
        }
        return translateSource;
    }

    private Map<String, Integer> getRankMap(Document doc) {
        Elements rankItems = doc.select("ul.rank-box > li");

        Map<String, Integer> rankMap = new HashMap<>();
        int rank = 1;
        for (Element rankItem : rankItems) {
            String starNameJa = rankItem.select("span").text().trim();
            rankMap.put(starNameJa, rank);
            rank++;
        }
        return rankMap;
    }

}
