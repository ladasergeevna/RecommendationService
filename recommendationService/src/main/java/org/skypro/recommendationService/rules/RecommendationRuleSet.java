package org.skypro.recommendationService.rules;
import org.skypro.recommendationService.dto.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<RecommendationDto> getRecommendation(UUID userId);
}
