package com.starlog_be.fortune;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.starlog_be.TranslationService.translateJaToKo;

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

            // rank 구하기
            Map<String, Integer> rankMap = getRankMap(doc);

            // 별자리별상세운세
            Elements starDetails = doc.select(".seiza-area > .seiza-box");
            for (Element starDetail : starDetails) {
                String starNameJa = starDetail.selectFirst(".seiza-txt").ownText().trim();
                String descriptionJa = starDetail.select(".read").text();
                String luckyColorJa = starDetail.selectFirst(".lucky-color-txt").nextSibling().toString().replace(":", "").trim();
                String luckyKeyJa = starDetail.selectFirst(".key-txt").nextSibling().toString().replace(":", "").trim();
                int moneyLuckyScore = starDetail.select(".icon-money").size();
                int loveLuckyScore = starDetail.select(".icon-love").size();
                int workLuckyScore = starDetail.select(".icon-work").size();
                int healthLuckyScore = starDetail.select(".icon-health").size();

                int starRank = rankMap.get(starNameJa);

                // 번역
                Star star = Star.findStarByOriginalName(starNameJa);
                String descriptionKo = translateJaToKo(descriptionJa);
                String luckyColorKo = translateJaToKo(luckyColorJa);
                String luckyKeyKo = translateJaToKo(luckyKeyJa);

                Fortune fortune = new Fortune(
                        today.toLocalDate(),
                        star,
                        starRank,
                        descriptionJa,
                        luckyColorJa,
                        luckyKeyJa,
                        descriptionKo,
                        luckyColorKo,
                        luckyKeyKo,
                        moneyLuckyScore,
                        loveLuckyScore,
                        workLuckyScore,
                        healthLuckyScore
                );

                fortuneRepository.save(fortune);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Integer> getRankMap(Document doc) {
        Map<String, Integer> rankMap = new HashMap<>();
        Elements rankItems = doc.select("ul.rank-box > li");
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
