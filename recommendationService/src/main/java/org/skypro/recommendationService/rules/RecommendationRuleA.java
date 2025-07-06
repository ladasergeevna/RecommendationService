package org.skypro.recommendationService.rules;

import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.repository.RecommendationsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация правила Invest 500
 */
@Component
public class RecommendationRuleA implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;

    public RecommendationRuleA(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    /**
     * Получает рекомендацию для пользователя на основе выполненных условий.
     *
     * @param userId UUID пользователя.
     * @return Optional содержащий RecommendationDto, если условия выполнены; иначе Optional.empty().
     */
    public Optional<RecommendationDto> getRecommendation(UUID userId) {
        boolean conditionMet = recommendationsRepository.checkUserConditionsA(userId);
        if (conditionMet) {
            RecommendationDto recommendation = new RecommendationDto();
            recommendation.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
            recommendation.setName("Invest 500");
            recommendation.setText("Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");
            return Optional.of(recommendation);
        } else {
            return Optional.empty();
        }
    }
}
