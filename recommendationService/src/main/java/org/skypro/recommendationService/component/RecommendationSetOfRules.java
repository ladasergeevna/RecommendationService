package org.skypro.recommendationService.component;

import org.skypro.recommendationService.enums.ProductType;
import org.skypro.recommendationService.enums.RuleQueryType;
import org.skypro.recommendationService.model.DepositTransactions;
import org.skypro.recommendationService.model.RecommendationsByRules;
import org.skypro.recommendationService.model.Rule;
import org.skypro.recommendationService.model.WithdrawTransaction;
import org.skypro.recommendationService.repository.RecommendationsByRuleRepository;
import org.skypro.recommendationService.repository.RecommendationsRepository;
import org.skypro.recommendationService.repository.RuleRepository;
import org.skypro.recommendationService.service.RuleStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RecommendationSetOfRules {

    @Autowired
    private RecommendationsRepository recommendationsRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RecommendationsByRuleRepository recommendationsByRuleRepository;
    @Autowired
    private RuleStatisticsService ruleStatisticsService;

    public List<RecommendationsByRules> recommendationSelection(UUID userId) {
        List<Rule> allRules = ruleRepository.findAll();

        Map<String, Integer> depositMap = new HashMap<>();
        Map<String, Integer> withdrawMap = new HashMap<>();

        DepositTransactions depositTransactions = recommendationsRepository.getDepositAmountByUserId(userId);
        WithdrawTransaction withdrawTransactions = recommendationsRepository.getWithdrawAmountByUserId(userId);

        for (ProductType type : ProductType.values()) {
            depositMap.put(type.name(), switch (type) {
                case DEBIT -> depositTransactions.getDebitAmount();
                case SAVING -> depositTransactions.getSavingAmount();
                case CREDIT -> depositTransactions.getCreditAmount();
                case INVEST -> depositTransactions.getInvestAmount();
            });

            withdrawMap.put(type.name(), switch (type) {
                case DEBIT -> withdrawTransactions.getDebitAmount();
                case SAVING -> withdrawTransactions.getSavingAmount();
                case CREDIT -> withdrawTransactions.getCreditAmount();
                case INVEST -> withdrawTransactions.getInvestAmount();
            });
        }

        List<RecommendationsByRules> matchedRecommendations = new ArrayList<>();

        List<RecommendationsByRules> allRecommendations = recommendationsByRuleRepository.findAll();

        allRecommendations.parallelStream().forEach(recommendation -> {
            List<Rule> associatedRules = ruleRepository.findByRecommendationProductId(recommendation.getProductId());

            if (allRulesMatch(userId, associatedRules, depositMap, withdrawMap)) {
                matchedRecommendations.add(recommendation);
            }
        });

        return matchedRecommendations;
    }

    public boolean allRulesMatch(UUID userId, List<Rule> rulesList,
                                 Map<String, Integer> depositMap, Map<String, Integer> withdrawMap) {
        boolean currentCheckPassed;
        boolean allChecksPassed = false;
        List<Integer> passedChecks = new ArrayList<>();
        int rulesCheckedCount = 0;

        Rule rule1 = rulesList.get(0);
        Rule rule2 = rulesList.get(1);
        Rule rule3 = rulesList.get(2);

        if (rule1.getQuery().equals(RuleQueryType.USER_OF.name())) {
            currentCheckPassed = userOfCheck(rule1.getArguments(), rule1.isNegate(), depositMap, withdrawMap);
            if (currentCheckPassed) passedChecks.add(1);
            ruleStatisticsService.incrementRuleCount(rule1.getId());
            rulesCheckedCount++;
        } else if (rule1.getQuery().equals(RuleQueryType.ACTIVE_USER_OF.name())) {
            currentCheckPassed = activeUserOfCheck(userId, rule1.getArguments(), rule1.isNegate());
            if (currentCheckPassed) passedChecks.add(1);
            rulesCheckedCount++;
            // Инкремент счетчика для этого правила
            ruleStatisticsService.incrementRuleCount(rule1.getId());
        }

        if (rule2.getQuery().equals(RuleQueryType.ACTIVE_USER_OF.name())) {
            currentCheckPassed = activeUserOfCheck(userId, rule2.getArguments(), rule2.isNegate());
            if (currentCheckPassed) passedChecks.add(1);
            ruleStatisticsService.incrementRuleCount(rule2.getId());
            rulesCheckedCount++;
        } else if (rule2.getQuery().equals(RuleQueryType.TRANSACTION_SUM_COMPARE.name())) {
            currentCheckPassed = transactionSumCompareCheck(rule2.getArguments(), rule2.isNegate(), depositMap, withdrawMap);
            if (currentCheckPassed) passedChecks.add(1);
            ruleStatisticsService.incrementRuleCount(rule2.getId());
            rulesCheckedCount++;
        }

        if (rule3.getQuery().equals(RuleQueryType.TRANSACTION_SUM_COMPARE.name())) {
            currentCheckPassed = transactionSumCompareCheck(rule3.getArguments(), rule3.isNegate(), depositMap, withdrawMap);
            if (currentCheckPassed) passedChecks.add(1);
            ruleStatisticsService.incrementRuleCount(rule3.getId());
            rulesCheckedCount++;
        } else if (rule3.getQuery().equals(RuleQueryType.TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW.name())) {
            currentCheckPassed = compareTransactionsByProduct(rule3.getArguments(), rule3.isNegate(), depositMap, withdrawMap);
            if (currentCheckPassed) passedChecks.add(1);
            ruleStatisticsService.incrementRuleCount(rule3.getId());
            rulesCheckedCount++;
        }

        if (rulesCheckedCount == 3 && passedChecks.size() == 3) {
            allChecksPassed = true;
        }

        return allChecksPassed;
    }

    public boolean userOfCheck(List<String> arguments, boolean negate,
                               Map<String, Integer> depositMap, Map<String, Integer> withdrawMap) {
        int totalAmount = depositMap.get(arguments.get(0)) + withdrawMap.get(arguments.get(0));
        return (!negate && totalAmount > 0) || (negate && totalAmount == 0);
    }

    public boolean activeUserOfCheck(UUID userId, List<String> arguments, boolean negate) {
        int transactionCount = recommendationsRepository.getCountTransactionsByProductName(userId, arguments.get(0));
        return (!negate && transactionCount >= 5) || (negate && transactionCount == 0);
    }

    public boolean transactionSumCompareCheck(List<String> arguments, boolean negate,
                                              Map<String, Integer> depositMap, Map<String, Integer> withdrawMap) {
        int amount = 0;
        String transactionType = arguments.get(1);

        if (transactionType.equals("DEPOSIT")) {
            amount = depositMap.get(arguments.get(0));
        } else if (transactionType.equals("WITHDRAW")) {
            amount = withdrawMap.get(arguments.get(0));
        }

        String compareSign = arguments.get(2);
        int compareValue = Integer.parseInt(arguments.get(3));

        return switch (compareSign) {
            case ">" -> negate ? amount <= compareValue : amount > compareValue;
            case "=" -> negate ? amount != compareValue : amount == compareValue;
            case "<" -> negate ? amount >= compareValue : amount < compareValue;
            default -> false;
        };
    }

    public boolean compareTransactionsByProduct(List<String> arguments, boolean negate,
                                                Map<String, Integer> depositMap, Map<String, Integer> withdrawMap) {
        String productType = arguments.get(0);
        String compareSign = arguments.get(1);

        int depositAmount = depositMap.get(productType);
        int withdrawAmount = withdrawMap.get(productType);

        return switch (compareSign) {
            case ">" -> negate ? depositAmount <= withdrawAmount : depositAmount > withdrawAmount;
            case "=" -> negate ? depositAmount != withdrawAmount : depositAmount == withdrawAmount;
            case "<" -> negate ? depositAmount >= withdrawAmount : depositAmount < withdrawAmount;
            default -> false;
        };
    }
}
