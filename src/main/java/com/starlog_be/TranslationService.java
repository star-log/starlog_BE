package com.starlog_be;

import com.deepl.api.Translator;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private static Translator translator;

    public TranslationService() {
        this.translator = new Translator("BTM1OxVLI4qyX28CT");
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
}
