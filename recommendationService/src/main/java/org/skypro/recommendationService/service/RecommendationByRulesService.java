package org.skypro.recommendationService.service;

import jakarta.transaction.Transactional;
import org.skypro.recommendationService.component.RecommendationSetOfRules;
import org.skypro.recommendationService.dto.RecommendationRuleDto;
import org.skypro.recommendationService.dto.RuleDto;
import org.skypro.recommendationService.model.RecommendationsByRules;
import org.skypro.recommendationService.model.Rule;
import org.skypro.recommendationService.repository.RecommendationsByRuleRepository;
import org.skypro.recommendationService.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationByRulesService {

    @Autowired
    private RecommendationsByRuleRepository recommendationsByRulesRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RecommendationSetOfRules recommendationSetOfRules;

    //получение рекоммендаций для пользователей
    public List<RecommendationsByRules> selectRecommendation(String userId){
        return recommendationSetOfRules.recommendationSelection(userId);
    }

    //Создание
    public RecommendationsByRules saveRecByRule(RecommendationRuleDto recomDto){

        RecommendationsByRules recommendation = new RecommendationsByRules();
        recommendation.setProductName(recomDto.getProduct_name());
        recommendation.setProductText(recomDto.getProduct_text());
        List<Rule> rules = new ArrayList<>();
        for (RuleDto ruleDto : recomDto.getRule()) {
            Rule rule = new Rule(ruleDto.getQuery(), ruleDto.getArguments(), ruleDto.isNegate(), recommendation);
            rules.add(rule);}

        List<Rule> sortedRuleList = sortRules(rules);
        recommendation.setRule(sortedRuleList);


        if (rules.size() != 3){return null;}
        return recommendationsByRulesRepository.save(recommendation);
    }

    public List<RecommendationsByRules> getAllRecsByRule(){
        return recommendationsByRulesRepository.findAll();
    }

    public void deleteRules(){
        int rulesSize = ruleRepository.findAll().size();
        int recommendationsSize = recommendationsByRulesRepository.findAll().size();

        if ((rulesSize + recommendationsSize) != 0){
            ruleRepository.deleteAll();
            recommendationsByRulesRepository.deleteAll();
        }
    }

    //удаление рекомендаций и правил по продукту
    @Transactional
    public void deleteRecommendationByProductId(UUID productId) {
        Optional<RecommendationsByRules> recommendationOpt = recommendationsByRulesRepository.findById(productId);

        if (recommendationOpt.isPresent()) {
            RecommendationsByRules recommendation = recommendationOpt.get();
            for (Rule rule : recommendation.getRule()) {
                ruleRepository.delete(rule);
            }
            recommendationsByRulesRepository.delete(recommendation);
        }
    }

    //Сортировка правил по querys: USER_OF -> ACTIVE_USER_OF ->  TRANSACTION_SUM_COMPARE -> TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW
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
