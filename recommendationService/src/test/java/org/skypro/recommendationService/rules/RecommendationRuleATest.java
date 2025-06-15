package org.skypro.recommendationService.rules;

import org.junit.jupiter.api.Test;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationRuleATest {

    private final RecommendationsRepository recommendationsRepository = mock(RecommendationsRepository.class);
    private final RecommendationRuleA recommendationRuleA = new RecommendationRuleA(recommendationsRepository);

    @Test
    void shouldReturnRecommendationWhenConditionMet() {
        UUID userId = UUID.randomUUID();
        when(recommendationsRepository.checkUserConditionsA(userId)).thenReturn(true);

        Optional<RecommendationDto> result = recommendationRuleA.getRecommendation(userId);

        assertTrue(result.isPresent());
        assertEquals("Invest 500", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenConditionNotMet() {
        UUID userId = UUID.randomUUID();
        when(recommendationsRepository.checkUserConditionsA(userId)).thenReturn(false);

        Optional<RecommendationDto> result = recommendationRuleA.getRecommendation(userId);

        assertTrue(result.isEmpty());
    }
}
