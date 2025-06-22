package org.skypro.recommendationService.rules;

import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.repository.RecommendationsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Правило C для рекомендаций.
 * Условие: пользователь не пользуется кредитами, имеет положительное сальдо и снятия на сумму более 100 000.
 * Если условие выполняется — предлагается кредитный продукт.
 */
@Component
public class RecommendationRuleC implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;

    public RecommendationRuleC(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }


    public Optional<RecommendationDto> getRecommendation(UUID userId) {
        boolean conditionMet = recommendationsRepository.checkUserConditionsC(userId);
        if (conditionMet) {
            RecommendationDto recommendation = new RecommendationDto();
            recommendation.setId(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
            recommendation.setName("Простой кредит");
            recommendation.setText("Откройте мир выгодных кредитов с нами!\n" +
                    "\n" +
                    "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" +
                    "\n" +
                    "Почему выбирают нас:\n" +
                    "\n" +
                    "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" +
                    "\n" +
                    "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
                    "\n" +
                    "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" +
                    "\n" +
                    "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!");
            return Optional.of(recommendation);
        } else {
            return Optional.empty();
        }
    }
}
