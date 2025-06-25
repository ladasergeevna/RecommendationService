package org.skypro.recommendationService.service;

import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис, отвечающий за получение рекомендаций для пользователя
 * на основе набора предопределённых и/или динамических правил.
 */
@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;

    /**
     * Конструктор сервиса рекомендаций.
     *
     * @param ruleSets список всех реализованных правил рекомендаций
     */
    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    /**
     * Возвращает список рекомендаций для конкретного пользователя.
     * Каждый элемент списка представляет отдельное правило, которое сработало.
     *
     * @param userId уникальный идентификатор пользователя
     * @return список рекомендаций, применимых к данному пользователю
     */
    public List<RecommendationDto> getRecommendations(UUID userId) {
        List<RecommendationDto> recommendations = new ArrayList<>();

        for (RecommendationRuleSet ruleSet : ruleSets) {
            Optional<RecommendationDto> recommendationOpt = ruleSet.getRecommendation(userId);
            recommendationOpt.ifPresent(recommendations::add);
        }

        return recommendations;
    }
}
