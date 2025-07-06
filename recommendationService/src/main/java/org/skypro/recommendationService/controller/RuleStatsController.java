package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.recommendationService.component.RuleStats;
import org.skypro.recommendationService.service.RuleStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * REST-контроллер для получения статистики срабатывания правил.
 */
@Tag(name = "RuleStatsController", description = "Контроллер для получения статистики срабатывания правил")
@RestController
public class RuleStatsController {

    @Autowired
    private RuleStatisticsService ruleStatisticsService;

    /**
     * Получает информацию о количестве срабатываний каждого правила
     *
     * @return ответ с количеством срабатываний каждого правила
     */
    @Operation(summary = "Получает информацию о количестве срабатываний каждого правила")
    @GetMapping("/rule/stats")
    public ResponseEntity<?> getAllStats() {
        List<RuleStats> statsList = ruleStatisticsService.getAllStats();

        var response = statsList.stream()
                .map(stat -> Map.of(
                        "rule_id", stat.getRuleId().toString(),
                        "count", String.valueOf(stat.getCount())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of("stats", response));
    }
}