package org.skypro.recommendationService.controller;

import org.skypro.recommendationService.dto.RecommendationRuleDto;
import org.skypro.recommendationService.model.RecommendationsByRules;
import org.skypro.recommendationService.service.RecommendationByRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationRuleController {

    @Autowired
    private RecommendationByRulesService recsByRulesService;

    @PostMapping
    public ResponseEntity<RecommendationsByRules> saveRecs(
            @RequestBody RecommendationRuleDto recDto){

        return ResponseEntity.ok(recsByRulesService.saveRecByRule(recDto));
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<RecommendationsByRules>> getRecForUser(@PathVariable("userId") String userId){
        List<RecommendationsByRules> recsList = recsByRulesService.selectRecommendation(userId);

        if (recsList == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(recsList);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteRecommendationByRule(@PathVariable("productId") UUID productId){
        recsByRulesService.deleteRecommendationByProductId(productId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/clear-all")
    public ResponseEntity<Void> deleteAll(){
        recsByRulesService.deleteRules();
        return ResponseEntity.ok().build();
    }
}
