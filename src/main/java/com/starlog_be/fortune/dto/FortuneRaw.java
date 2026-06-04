package com.starlog_be.fortune.dto;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record FortuneRaw(
        Map<String, Integer> rankMap,
        Elements starDetails,
        LocalDateTime today
) {

    public static FortuneRaw of(Document doc, LocalDateTime today) {
        verifyIsToday(doc, today);
        Elements starDetails = doc.select(".seiza-area > .seiza-box");
        return new FortuneRaw(getRankMap(doc), starDetails, today);
    }

    private static void verifyIsToday(Document doc, LocalDateTime today) {
        String siteDateText = doc.select(".ttl-area").first().text();
        if (!siteDateText.contains(today.getMonthValue() + "月" + today.getDayOfMonth() + "日")) {
            System.out.println("아직 오늘의 운세가 올라오지 않았습니다. 현재일시: " + today);
        }
    }

    private static Map<String, Integer> getRankMap(Document doc) {
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

    public List<String> getTranslateSource() {
        List<String> translateSource = new ArrayList<>();
        for (Element starDetail : starDetails) {
            String descriptionJa = starDetail.select(".read").text();
            String luckyColorJa = starDetail.selectFirst(".lucky-color-txt").nextSibling().toString().replaceAll("^.*?[:：]", "").trim();
            String luckyKeyJa = starDetail.selectFirst(".key-txt").nextSibling().toString().replaceAll("^.*?[:：]", "").trim();

            translateSource.add(descriptionJa);
            translateSource.add(luckyColorJa);
            translateSource.add(luckyKeyJa);
        }
        return translateSource;
    }
}
