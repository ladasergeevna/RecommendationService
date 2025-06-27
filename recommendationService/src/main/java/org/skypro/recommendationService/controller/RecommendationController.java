package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.model.DepositTransactions;
import org.skypro.recommendationService.model.RecommendationResponse;
import org.skypro.recommendationService.model.User;
import org.skypro.recommendationService.model.WithdrawTransaction;
import org.skypro.recommendationService.service.RecommendationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

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

    @GetMapping(path = "/deposit/{userId}")
    public DepositTransactions getDepById(@PathVariable("userId") String id){
        return recommendationService.getDepAmountById(id);
    }

    @GetMapping(path = "/withdraw/{userId}")
    public WithdrawTransaction getWithById(@PathVariable("userId") String id){
        return recommendationService.getWithAmountById(id);
    }
}