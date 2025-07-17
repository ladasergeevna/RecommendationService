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
/**
 * Сервис для получения рекомендаций и связанных с ними данных.
 */
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

    /**
     * Получает список рекомендаций для пользователя по его ID.
     *
     * @param userId UUID идентификатора пользователя.
     * @return список рекомендаций, соответствующих пользователю.
     */
    public List<RecommendationDto> getRecommendations(UUID userId) {
        List<RecommendationDto> recommendations = new ArrayList<>();

        for (RecommendationRuleSet ruleSet : ruleSets) {
            Optional<RecommendationDto> recommendationOpt = ruleSet.getRecommendation(userId);
            recommendationOpt.ifPresent(recommendations::add);
        }

        return recommendations;
    }

    /**
     * Получает информацию о депозитных транзакциях пользователя по его ID.
     *
     * @param userId UUID идентификатора пользователя.
     * @return объект DepositTransactions с информацией о сумме депозитов.
     */
    public DepositTransactions getDepAmountById(UUID userId){

        return recommendationsRepository.getDepositAmountByUserId(userId);
    }

    /**
     * Получает сумму снятий транзакций пользователя по его ID.
     *
     * @param userId UUID идентификатора пользователя.
     * @return объект WithdrawTransaction с информацией о сумме снятий.
     */
    public WithdrawTransaction getWithAmountById(UUID userId){

        return recommendationsRepository.getWithdrawAmountByUserId(userId);
    }
}
