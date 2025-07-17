package org.skypro.recommendationService.model;

import org.skypro.recommendationService.dto.RecommendationDto;

import java.util.List;
import java.util.UUID;

/**
 * DTO-класс для ответа API с рекомендациями пользователя.
 * Содержит идентификатор пользователя и список рекомендаций.
 */
public class RecommendationResponse {
    private UUID userId;
    private List<RecommendationDto> recommendations;

    public RecommendationResponse(UUID userId, List<RecommendationDto> recommendations) {
        this.userId = userId;
        this.recommendations = recommendations;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public List<RecommendationDto> getRecommendations() { return recommendations; }
    public void setRecommendations(List<RecommendationDto> recommendations) { this.recommendations = recommendations; }
}
