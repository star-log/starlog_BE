package com.starlog_be.fortune;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

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

    public void execute() {
        try {
            Document doc = Jsoup.connect(fortuneConfig.fortuneUrl())
                    .userAgent(fortuneConfig.userAgent())
                    .timeout(fortuneConfig.timeout())
                    .get();

            LocalDateTime today = LocalDateTime.now();
            verifyIsToday(doc, today);

            Map<String, Integer> rankMap = getRankMap(doc);

            Elements starDetails = doc.select(".seiza-area > .seiza-box");

            List<String> translateSource = getTranslateSource(starDetails);

            List<String> koTexts = translateJaTextsToKoTexts(translateSource);

            int koTextsIndex = 0;
            List<Fortune> fortuneList = new ArrayList<>();
            for (Element starDetail : starDetails) {
                String starNameJa = starDetail.selectFirst(".seiza-txt").ownText().trim();
                int moneyLuckyScore = starDetail.select(".icon-money").size();
                int loveLuckyScore = starDetail.select(".icon-love").size();
                int workLuckyScore = starDetail.select(".icon-work").size();
                int healthLuckyScore = starDetail.select(".icon-health").size();

                Star star = Star.findStarByOriginalName(starNameJa);
                int starRank = rankMap.get(starNameJa);

                String descriptionKo = koTexts.get(koTextsIndex++);
                String luckyColorKo = koTexts.get(koTextsIndex++);
                String luckyKeyKo = koTexts.get(koTextsIndex++);

                Fortune fortune = new Fortune(
                        today.toLocalDate(),
                        star,
                        starRank,
                        descriptionKo,
                        luckyColorKo,
                        luckyKeyKo,
                        moneyLuckyScore,
                        loveLuckyScore,
                        workLuckyScore,
                        healthLuckyScore
                );
                fortuneList.add(fortune);
            }

            fortuneRepository.saveAll(fortuneList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void verifyIsToday(Document doc, LocalDateTime today) {
        String siteDateText = doc.select(".ttl-area").first().text();
        if (siteDateText.contains(today.getMonthValue() + "月" + today.getDayOfMonth() + "日")) {
            System.out.println("아직 오늘의 운세가 올라오지 않았습니다. 현재일시: " + today);
        }
    }
}
