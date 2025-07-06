package org.skypro.recommendationService.service;

import org.skypro.recommendationService.component.RuleStats;
import org.skypro.recommendationService.repository.RuleStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для сбора статистики о частоте срабатывания правил
 */
@Service
public class RuleStatisticsService {

    @Autowired
    private RuleStatisticsRepository ruleStatisticsRepository;

    /**
     * Получает список всех статистических данных по правилам.
     *
     * @return список объектов RuleStats, содержащих статистику по каждому правилу.
     */
    public List<RuleStats> getAllStats() {
        return ruleStatisticsRepository.findAll();
    }

    /**
     * Увеличивает счетчик срабатываний правила.
     *
     * @param ruleId UUID правила, для которого нужно увеличить счетчик.
     */
    public void incrementRuleCount(UUID ruleId) {
        RuleStats stats = ruleStatisticsRepository.findById(ruleId).orElseGet(() -> new RuleStats(ruleId));
        stats.increment();
        ruleStatisticsRepository.save(stats);
    }

    /**
     * Обнуляет счетчика срабатываний правила.
     *
     * @param ruleId UUID правила, для которого нужно сбросить счетчик.
     */
    public void resetRuleCounter(UUID ruleId) {
        if (ruleStatisticsRepository.existsById(ruleId)) {
            RuleStats stats = ruleStatisticsRepository.findById(ruleId).get();
            stats.reset();
            ruleStatisticsRepository.save(stats);
        }
    }
}