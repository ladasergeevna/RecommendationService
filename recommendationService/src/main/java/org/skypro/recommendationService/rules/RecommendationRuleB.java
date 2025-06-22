package org.skypro.recommendationService.rules;

import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Правило B для рекомендаций.
 * Условие: пользователь активно использует дебетовые продукты, делает крупные снятия, но при этом накапливает больше, чем тратит.
 * Если условие выполняется — предлагается продукт «Копилка».
 */
@Component
public class RecommendationRuleB implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationRuleB(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDto> getRecommendation(UUID userId) {
        boolean conditionMet = recommendationsRepository.checkUserConditionsB(userId);
        if (conditionMet) {
            RecommendationDto recommendation = new RecommendationDto();
            recommendation.setId(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"));
            recommendation.setName("Top Saving");
            recommendation.setText("Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
                    "\n" +
                    "Преимущества «Копилки»:\n" +
                    "\n" +
                    "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" +
                    "\n" +
                    "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" +
                    "\n" +
                    "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" +
                    "\n" +
                    "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!");
            return Optional.of(recommendation);
        } else {
            return Optional.empty();
        }
    }
}