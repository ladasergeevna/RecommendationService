package org.skypro.recommendationService.repository;

import org.skypro.recommendationService.model.RecommendationsByRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecommendationsByRuleRepository extends JpaRepository<RecommendationsByRules, UUID> {

    @Query(value = "SELECT product_name, product_text FROM RECOMMENDATIONS rec JOIN rules r ON r.product_id = rec.product_id WHERE r.product_id = :productId LIMIT 1", nativeQuery = true)
    RecommendationsByRules getRecommendationByProductId(@Param("productId") String productId);

}
