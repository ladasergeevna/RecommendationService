package org.skypro.recommendationService.rules;
import org.skypro.recommendationService.dto.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс для реализации набора правил рекомендаций.
 * Все конкретные правила (фиксированные или динамические) должны реализовать этот интерфейс.
 */
public interface RecommendationRuleSet {

    /**
     * Возвращает рекомендацию для пользователя, если правило применимо.
     *
     * @param userId идентификатор пользователя, для которого формируется рекомендация
     * @return {@link Optional} с рекомендацией, если правило выполнено; пустой Optional в противном случае
     */
    Optional<RecommendationDto> getRecommendation(UUID userId);
}
