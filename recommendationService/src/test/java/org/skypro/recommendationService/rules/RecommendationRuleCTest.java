package org.skypro.recommendationService.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationRuleCTest {

    @Test
    @DisplayName("Should return recommendation when condition is met")
    void testRecommendationReturnedWhenConditionMet() {
        RecommendationsRepository mockRepo = mock(RecommendationsRepository.class);
        UUID userId = UUID.randomUUID();

        when(mockRepo.checkUserConditionsC(userId)).thenReturn(true);

        RecommendationRuleC rule = new RecommendationRuleC(mockRepo);
        Optional<RecommendationDto> result = rule.getRecommendation(userId);

        assertTrue(result.isPresent());
        assertEquals("Простой кредит", result.get().getName());
        assertEquals(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), result.get().getId());
    }

    @Test
    @DisplayName("Should return empty when condition is not met")
    void testNoRecommendationWhenConditionNotMet() {
        RecommendationsRepository mockRepo = mock(RecommendationsRepository.class);
        UUID userId = UUID.randomUUID();

        when(mockRepo.checkUserConditionsC(userId)).thenReturn(false);

        RecommendationRuleC rule = new RecommendationRuleC(mockRepo);
        Optional<RecommendationDto> result = rule.getRecommendation(userId);

        assertFalse(result.isPresent());
    }
}

