package org.skypro.recommendationService.service;

import jakarta.transaction.Transactional;
import org.skypro.recommendationService.component.RecommendationSetOfRules;
import org.skypro.recommendationService.component.RuleStats;
import org.skypro.recommendationService.dto.RecommendationRuleDto;
import org.skypro.recommendationService.dto.RuleDto;
import org.skypro.recommendationService.model.RecommendationsByRules;
import org.skypro.recommendationService.model.Rule;
import org.skypro.recommendationService.repository.RecommendationsByRuleRepository;
import org.skypro.recommendationService.repository.RuleRepository;
import org.skypro.recommendationService.repository.RuleStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * Сервис для работы с рекомендациями на основе динамических правил.
 */
@Service
public class RecommendationByRulesService {

    @Autowired
    private RecommendationsByRuleRepository recommendationsByRulesRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RecommendationSetOfRules recommendationSetOfRules;
    @Autowired
    private RuleStatisticsService ruleStatisticsService;
    @Autowired
    private RuleStatisticsRepository ruleStatisticsRepository;

    public List<RecommendationsByRules> selectRecommendation(UUID userId) {
        return recommendationSetOfRules.recommendationSelection(userId);
    }

    /**
     * Создает и сохраняет новую рекомендацию на основе переданного DTO.
     *
     * @param dto DTO с данными для создания рекомендации.
     * @return созданная рекомендация, или null, если не удалось сохранить.
     */
    public RecommendationsByRules saveRecByRule(RecommendationRuleDto dto) {
        RecommendationsByRules recommendation = new RecommendationsByRules();
        recommendation.setProductName(dto.getProductName());
        recommendation.setProductText(dto.getProductText());

        List<Rule> rules = new ArrayList<>();
        for (RuleDto ruleDto : dto.getRule()) {
            rules.add(new Rule(ruleDto.getQuery(), ruleDto.getArguments(), ruleDto.isNegate(), null));
        }

        List<Rule> sortedRules = sortRules(rules);
        recommendation.setRule(sortedRules);

        for (Rule rule : sortedRules) {
            rule.setRecommendation(recommendation);
            if (!ruleStatisticsRepository.existsById(rule.getId())) {
                RuleStats rs = new RuleStats(rule.getId());
                ruleStatisticsRepository.save(rs);
            }
        }

        if (sortedRules.size() != 3) return null;

        try {
            return recommendationsByRulesRepository.save(recommendation);
        } catch (Exception e) {
            System.err.println("❌ Error saving recommendation: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Получает все рекомендации, сохраненные в базе данных.
     *
     * @return список всех рекомендаций.
     */
    public List<RecommendationsByRules> getAllRecsByRule() {
        return recommendationsByRulesRepository.findAll();
    }

    /**
     * Удаляет все правила и рекомендации из базы данных.
     */
    public void deleteRules() {
        int rulesSize = ruleRepository.findAll().size();
        int recommendationsSize = recommendationsByRulesRepository.findAll().size();

        if ((rulesSize + recommendationsSize) != 0) {
            ruleRepository.deleteAll();
            recommendationsByRulesRepository.deleteAll();
        }
    }

    /**
     * Удаляет рекомендацию по ID продукта и связанные с ней правила.
     *
     * @param productId UUID продукта, рекомендации которого нужно удалить.
     */
    @Transactional
    public void deleteRecommendationByProductId(UUID productId) {
        Optional<RecommendationsByRules> recommendationOpt = recommendationsByRulesRepository.findById(productId);

        if (recommendationOpt.isPresent()) {
            RecommendationsByRules recommendation = recommendationOpt.get();
            for (Rule rule : recommendation.getRule()) {
                ruleStatisticsService.resetRuleCounter(rule.getId());
                ruleRepository.delete(rule);
            }
            recommendationsByRulesRepository.delete(recommendation);
        }
    }

    /**
     * Сортирует список правил.
     *
     * @param ruleList список правил для сортировки.
     * @return отсортированный список правил.
     */
    private List<Rule> sortRules(List<Rule> ruleList) {
        List<String> order = Arrays.asList(
                "USER_OF",
                "ACTIVE_USER_OF",
                "TRANSACTION_SUM_COMPARE",
                "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW"
        );

        ruleList.sort(Comparator.comparingInt(rule -> order.indexOf(rule.getQuery())));
        return ruleList;
    }
}
