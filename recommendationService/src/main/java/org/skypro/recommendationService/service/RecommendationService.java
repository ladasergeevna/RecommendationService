package org.skypro.recommendationService.service;

import org.skypro.recommendationService.component.RecommendationSetOfRules;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.model.DepositTransactions;
import org.skypro.recommendationService.model.User;
import org.skypro.recommendationService.model.WithdrawTransaction;
import org.skypro.recommendationService.repository.RecommendationsRepository;
import org.skypro.recommendationService.rules.RecommendationRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;

    private RecommendationSetOfRules recommendationSetOfRules;

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationService(List<RecommendationRuleSet> ruleSets, RecommendationsRepository recommendationsRepository) {
        this.ruleSets = ruleSets;
        this.recommendationsRepository = recommendationsRepository;
    }

    public List<RecommendationDto> getRecommendations(UUID userId) {
        List<RecommendationDto> recommendations = new ArrayList<>();

        for (RecommendationRuleSet ruleSet : ruleSets) {
            Optional<RecommendationDto> recommendationOpt = ruleSet.getRecommendation(userId);
            recommendationOpt.ifPresent(recommendations::add);
        }

        return recommendations;
    }

    public DepositTransactions getDepAmountById(UUID userId){

        return recommendationsRepository.getDepositAmountByUserId(userId);
    }

    public WithdrawTransaction getWithAmountById(UUID userId){

        return recommendationsRepository.getWithdrawAmountByUserId(userId);
    }
}
