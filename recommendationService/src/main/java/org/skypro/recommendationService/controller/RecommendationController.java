package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.recommendationService.dto.RecommendationDto;

import org.skypro.recommendationService.model.DepositTransactions;
import org.skypro.recommendationService.model.RecommendationResponse;
import org.skypro.recommendationService.model.User;
import org.skypro.recommendationService.model.WithdrawTransaction;

import org.skypro.recommendationService.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
/**
 * REST-контроллер для получения рекомендаций пользователем.
 */
@Tag(name = "RecommendationController", description = "Контроллер для получения рекомендаций пользователем")
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    @Autowired
    RecommendationResponse recommendationResponse;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Получает рекомендации для пользователя по его ID.
     *
     * @param userId ID пользователя.
     * @return ответ с объектом RecommendationResponse, содержащим рекомендации.
     */
    @Operation(summary = "Получает рекомендации для пользователя по его ID")
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
    /**
     * Получает информацию о депозитах пользователя по его ID.
     *
     * @param id ID пользователя.
     * @return объект DepositTransactions с информацией о депозитах.
     */
    @Operation(summary = "Получает информацию о депозитах пользователя по его ID")
    @GetMapping(path = "/deposit/{userId}")
    public DepositTransactions getDepById(@PathVariable("userId") UUID id){
        return recommendationService.getDepAmountById(id);
    }
    /**
     * Получает информацию о снятиях пользователя по его ID.
     *
     * @param id ID пользователя.
     * @return объект WithdrawTransaction с информацией о снятиях.
     */
    @Operation(summary = "Получает информацию о снятиях пользователя по его ID")
    @GetMapping(path = "/withdraw/{userId}")
    public WithdrawTransaction getWithById(@PathVariable("userId") UUID id){
        return recommendationService.getWithAmountById(id);
    }
}