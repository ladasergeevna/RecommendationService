package org.skypro.recommendationService.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.recommendationService.component.RecommendationSetOfRules;
import org.skypro.recommendationService.dto.RuleDto;
import org.skypro.recommendationService.dto.RecommendationRuleDto;
import org.skypro.recommendationService.model.Rule;
import org.skypro.recommendationService.model.RecommendationsByRules;
import org.skypro.recommendationService.repository.RecommendationsByRuleRepository;
import org.skypro.recommendationService.repository.RuleRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecommendationByRulesServiceTest {
    @Mock
    private RecommendationsByRuleRepository recommendationsByRulesRepository;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private RecommendationSetOfRules recommendationSetOfRules;

    @InjectMocks
    private RecommendationByRulesService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Тестируем метод сохранения рекомендаций
    @Test
    public void testSaveRecByRule_Success() {

        RecommendationRuleDto dto = createSampleRecommendationRuleDto();
        RecommendationsByRules expected = new RecommendationsByRules();
        expected.setProductName("Product");
        expected.setProductText("Text");
        expected.setRule(createSampleRules());

        when(recommendationsByRulesRepository.save(any(RecommendationsByRules.class))).thenReturn(expected);

        RecommendationsByRules result = service.saveRecByRule(dto);

        assertNotNull(result);
        assertEquals(expected.getProductName(), result.getProductName());
        assertEquals(expected.getProductText(), result.getProductText());
        assertEquals(expected.getRule().size(), result.getRule().size());
    }

    // Тестируем очистку всех правил и рекомендаций
    @Test
    public void testDeleteRules() {

        when(ruleRepository.findAll()).thenReturn(Collections.singletonList(new Rule()));
        when(recommendationsByRulesRepository.findAll()).thenReturn(Collections.singletonList(new RecommendationsByRules()));

        service.deleteRules();

        verify(ruleRepository, times(1)).deleteAll();
        verify(recommendationsByRulesRepository, times(1)).deleteAll();
    }

    // Тестируем удаление рекомендаций по продукту
    @Test
    public void testDeleteRecommendationByProductId() {
        // Arrange
        UUID productId = UUID.randomUUID();
        RecommendationsByRules existingRecommendation = new RecommendationsByRules();
        existingRecommendation.setProductId(productId);
        existingRecommendation.setRule(Collections.singletonList(new Rule()));

        when(recommendationsByRulesRepository.findById(productId)).thenReturn(Optional.of(existingRecommendation));

        service.deleteRecommendationByProductId(productId);

        verify(ruleRepository, times(1)).delete(any(Rule.class));
        verify(recommendationsByRulesRepository, times(1)).delete(existingRecommendation);
    }

    // Helper methods
    private RecommendationRuleDto createSampleRecommendationRuleDto() {
        RecommendationRuleDto dto = new RecommendationRuleDto();
        dto.setProductName("Sample Product");
        dto.setProductText("Sample Text");
        dto.setRule(createSampleRuleDtos());
        return dto;
    }

    private List<RuleDto> createSampleRuleDtos() {
        List<RuleDto> rules = new ArrayList<>();
        rules.add(new RuleDto("USER_OF", Collections.singletonList(""), false));
        rules.add(new RuleDto("ACTIVE_USER_OF", Collections.singletonList(""), false));
        rules.add(new RuleDto("TRANSACTION_SUM_COMPARE", Collections.singletonList(""), false));
        return rules;
    }

    private List<Rule> createSampleRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule("USER_OF", Collections.singletonList(""), false, null));
        rules.add(new Rule("ACTIVE_USER_OF", Collections.singletonList(""), false, null));
        rules.add(new Rule("TRANSACTION_SUM_COMPARE", Collections.singletonList(""), false, null));
        return rules;
    }
}