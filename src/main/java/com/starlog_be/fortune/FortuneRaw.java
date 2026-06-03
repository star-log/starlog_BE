package com.starlog_be.fortune;

import org.jsoup.nodes.Document;

import java.time.LocalDateTime;

public class FortuneRaw {

    public static FortuneRaw of(Document doc, LocalDateTime today) {
        verifyIsToday(doc, today);
    }

    private static void verifyIsToday(Document doc, LocalDateTime today) {
        String siteDateText = doc.select(".ttl-area").first().text();
        if (siteDateText.contains(today.getMonthValue() + "月" + today.getDayOfMonth() + "日")) {
            System.out.println("아직 오늘의 운세가 올라오지 않았습니다. 현재일시: " + today);
        }
    }
}
