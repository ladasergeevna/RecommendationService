//package org.skypro.recommendationService.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.skypro.recommendationService.dto.RecommendationDto;
//import org.skypro.recommendationService.rules.RecommendationRuleSet;
//
//import java.util.Arrays;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class RecommendationServiceTest {
//
//    private RecommendationService recommendationService;
//    private RecommendationRuleSet ruleA;
//    private RecommendationRuleSet ruleB;
//    private RecommendationRuleSet ruleC;
//
//    private final UUID userId = UUID.randomUUID();
//
//    @BeforeEach
//    void setUp() {
//        ruleA = Mockito.mock(RecommendationRuleSet.class);
//        ruleB = Mockito.mock(RecommendationRuleSet.class);
//        ruleC = Mockito.mock(RecommendationRuleSet.class);
//
//        recommendationService = new RecommendationService(Arrays.asList(ruleA, ruleB, ruleC));
//    }
//
//    @Test
//    void testGetRecommendations_AllRulesReturnRecommendation() {
//        RecommendationDto dtoA = new RecommendationDto(UUID.randomUUID(), "Rec A", "Text A");
//        RecommendationDto dtoB = new RecommendationDto(UUID.randomUUID(), "Rec B", "Text B");
//
//        when(ruleA.getRecommendation(userId)).thenReturn(Optional.of(dtoA));
//        when(ruleB.getRecommendation(userId)).thenReturn(Optional.of(dtoB));
//        when(ruleC.getRecommendation(userId)).thenReturn(Optional.empty());
//
//        List<RecommendationDto> result = recommendationService.getRecommendations(userId);
//
//        assertEquals(2, result.size());
//        assertTrue(result.contains(dtoA));
//        assertTrue(result.contains(dtoB));
//
//        verify(ruleA).getRecommendation(userId);
//        verify(ruleB).getRecommendation(userId);
//        verify(ruleC).getRecommendation(userId);
//    }
//
//    @Test
//    void testGetRecommendations_NoRecommendations() {
//        when(ruleA.getRecommendation(userId)).thenReturn(Optional.empty());
//        when(ruleB.getRecommendation(userId)).thenReturn(Optional.empty());
//        when(ruleC.getRecommendation(userId)).thenReturn(Optional.empty());
//
//        List<RecommendationDto> result = recommendationService.getRecommendations(userId);
//
//        assertTrue(result.isEmpty());
//    }
//}
