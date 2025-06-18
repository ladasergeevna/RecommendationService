package org.skypro.recommendationService.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.repository.RecommendationsRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationRuleBTest {

    @Test
    @DisplayName("Should return recommendation when condition B is met")
    void testRecommendationReturnedWhenConditionMet() {
        RecommendationsRepository mockRepo = mock(RecommendationsRepository.class);
        RecommendationRuleB rule = new RecommendationRuleB(mockRepo);
        UUID userId = UUID.randomUUID();

        when(mockRepo.checkUserConditionsB(userId)).thenReturn(true);

        Optional<RecommendationDto> result = rule.getRecommendation(userId);

        assertTrue(result.isPresent());
        assertEquals("Top Saving", result.get().getName());
    }

    @Test
    @DisplayName("Should return empty when condition B is not met")
    void testRecommendationNotReturnedWhenConditionNotMet() {
        RecommendationsRepository mockRepo = mock(RecommendationsRepository.class);
        RecommendationRuleB rule = new RecommendationRuleB(mockRepo);
        UUID userId = UUID.randomUUID();

        when(mockRepo.checkUserConditionsB(userId)).thenReturn(false);

        Optional<RecommendationDto> result = rule.getRecommendation(userId);

        assertTrue(result.isEmpty());
    }
}