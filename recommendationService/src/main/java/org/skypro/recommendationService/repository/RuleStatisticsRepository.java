package org.skypro.recommendationService.repository;

import org.skypro.recommendationService.component.RuleStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RuleStatisticsRepository extends JpaRepository<RuleStats, UUID> {
}
