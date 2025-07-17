package org.skypro.recommendationService.service;

import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public List<RecommendationDto> getRecommendations(UUID userId) {
        List<RecommendationDto> recommendations = new ArrayList<>();

        for (RecommendationRuleSet ruleSet : ruleSets) {
            Optional<RecommendationDto> recommendationOpt = ruleSet.getRecommendation(userId);
            recommendationOpt.ifPresent(recommendations::add);
        }

        return recommendations;
    }
}
