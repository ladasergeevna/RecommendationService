package org.skypro.recommendationService.service;

import org.skypro.recommendationService.component.RuleStats;
import org.skypro.recommendationService.repository.RuleStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RuleStatisticsService {

    @Autowired
    private RuleStatisticsRepository ruleStatisticsRepository;

    // Получить статистику по всем правилам
    public List<RuleStats> getAllStats() {
        return ruleStatisticsRepository.findAll();
    }

    // Инкрементировать счетчик по правилу
    public void incrementRuleCount(UUID ruleId) {
        RuleStats stats = ruleStatisticsRepository.findById(ruleId).orElseGet(() -> new RuleStats(ruleId));
        stats.increment();
        ruleStatisticsRepository.save(stats);
    }

    // Обнулить счетчик по правилу (при удалении)
    public void resetRuleCounter(UUID ruleId) {
        if (ruleStatisticsRepository.existsById(ruleId)) {
            RuleStats stats = ruleStatisticsRepository.findById(ruleId).get();
            stats.reset();
            ruleStatisticsRepository.save(stats);
        }
    }
}