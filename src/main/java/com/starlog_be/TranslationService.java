package com.starlog_be;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationService {

    private static Translator translator;

    public TranslationService() {
        this.translator = new Translator("{deepl.api-key}");
    }

    public static final String translateJaToKo(String originalText) {
        if (originalText == null || originalText.isBlank()) { return ""; }

        try {
            return translator.translateText(originalText, "JA", "KO").getText();
        } catch (Exception e) {
            System.out.println("번역 중 에러 발생. 원문 반환.");
            return originalText;
        }
    }

    public static List<String> translateJaTextsToKoTexts(List<String> originalTexts) {
        try {
            List<TextResult> resultTexts = translator.translateText(originalTexts, "JA", "KO");

            return resultTexts.stream()
                    .map(TextResult::getText)
                    .toList();

        } catch (Exception e) {
            System.out.println("번역 중 에러 발생. 원문 반환.");
            return originalTexts;
        }
    }
}
