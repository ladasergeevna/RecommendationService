package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.dto.RecommendationResponse;
import org.skypro.recommendationService.service.RecommendationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для получения рекомендаций для конкретного пользователя.
 * Обрабатывает HTTP-запросы по пути /api/recommendations.
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Конструктор контроллера.
     *
     * @param recommendationService сервис, обрабатывающий бизнес-логику рекомендаций
     */
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Возвращает рекомендации для указанного пользователя.
     * Используется HTTP GET-запрос по пути /api/recommendations/{userId}.
     *
     * @param userId уникальный идентификатор пользователя
     * @return JSON-ответ с рекомендациями
     */
    @GetMapping("/{userId}")
    public ResponseEntity<RecommendationResponse> getRecommendations(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable UUID userId) {
        List<RecommendationDto> recommendations = recommendationService.getRecommendations(userId);
        RecommendationResponse response = new RecommendationResponse(userId, recommendations);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}