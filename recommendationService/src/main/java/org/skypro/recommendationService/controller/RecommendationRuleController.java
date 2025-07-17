package org.skypro.recommendationService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skypro.recommendationService.dto.RecommendationRuleDto;
import org.skypro.recommendationService.model.RecommendationsByRules;
import org.skypro.recommendationService.service.RecommendationByRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
/**
 * REST-контроллер для управления (создания, удаления) правилами.
 */
@Tag(name = "RecommendationRuleController", description = "Контроллер для управления (создания, удаления) правилами")
@RestController
@RequestMapping("/recommendation")
public class RecommendationRuleController {

    @Autowired
    private RecommendationByRulesService recsByRulesService;

    /**
     * Создание нового правила.
     *
     * @return ответ о сохранении правила
     */
    @Operation(summary = "Создание нового правила")
    @PostMapping
    public ResponseEntity<RecommendationsByRules> saveRecs(
            @RequestBody RecommendationRuleDto recDto){

        return ResponseEntity.ok(recsByRulesService.saveRecByRule(recDto));
    }
    /**
     * Получает правила для пользователя по его идентификатору.
     *
     * @param userId уникальный идентификатор пользователя.
     * @return ответ со списком RecommendationsByRules, если данные найдены
     */
    @Operation(summary = "Получает правила для пользователя по его идентификатору")
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<RecommendationsByRules>> getRecForUser(@PathVariable("userId") UUID userId){
        List<RecommendationsByRules> recsList = recsByRulesService.selectRecommendation(userId);

        if (recsList == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(recsList);
    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param productId уникальный идентификатор продукта.
     * @return сообщение об удалении правила
     */
    @Operation(summary = "Удаляет продукт по его идентификатору")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteRecommendationByRule(@PathVariable("productId") UUID productId){
        recsByRulesService.deleteRecommendationByProductId(productId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Удаляет все правила.
     *
     * @return сообщение об удалении
     */
    @Operation(summary = "Удаляет все правила")
    @GetMapping(path = "/clear-all")
    public ResponseEntity<Void> deleteAll(){
        recsByRulesService.deleteRules();
        return ResponseEntity.ok().build();
    }
}
