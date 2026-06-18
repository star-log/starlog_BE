package com.starlog_be.fortune;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.starlog_be.fortune.domain.Fortune;
import com.starlog_be.fortune.domain.Star;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncTodayFortuneDataService {
    private final FortuneRepository fortuneRepository;
    private final FortuneConfig fortuneConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String response;

    @Transactional
    public void execute() {
        LocalDate today = LocalDate.now();
        String fortuneRaw = getFortuneRaw();
        List<FortuneRaw> fortuneRaws = objectMapper.readValue(
                fortuneRaw,
                new TypeReference<List<FortuneRaw>>() {}
        );

        List<Fortune> fortunes = fortuneRaws.stream()
                .map(raw -> new Fortune(
                        today,
                        Star.findByName(raw.starName()),
                        raw.rank(),
                        raw.description(),
                        raw.luckyColor(),
                        raw.luckyKey(),
                        raw.moneyScore(),
                        raw.loveScore(),
                        raw.workScore(),
                        raw.healthScore()
                ))
                .toList();

        fortuneRepository.saveAll(fortunes);
    }

    private String getFortuneRaw() {
        try {
            Client client = Client.builder().apiKey(fortuneConfig.apiKey()).build();
            GenerateContentConfig config = GenerateContentConfig.builder().responseMimeType("application/json").build();
            return client.models.generateContent(fortuneConfig.model(), fortuneConfig.prompt(), config).text();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
