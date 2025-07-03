package org.skypro.recommendationService.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.recommendationService.component.RecommendationSetOfRules;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.model.DepositTransactions;
import org.skypro.recommendationService.model.WithdrawTransaction;
import org.skypro.recommendationService.repository.RecommendationsRepository;
import org.skypro.recommendationService.rules.RecommendationRuleSet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecommendationServiceTest {

    private RecommendationSetOfRules recommendationSetOfRules;

    @Mock
    private RecommendationsRepository repository;

    @InjectMocks
    private RecommendationService recommendationService;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnEmptyListWhenNoRulesProvideRecommendations() {

        var mockRuleSet1 = mock(RecommendationRuleSet.class);
        when(mockRuleSet1.getRecommendation(any())).thenReturn(Optional.empty());

        var mockedRepository = mock(RecommendationsRepository.class);

        var service = new RecommendationService(List.of(mockRuleSet1), mockedRepository);

        List<RecommendationDto> result = service.getRecommendations(userId);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnNonEmptyListWithValidRecommendations() {

        var mockRuleSet1 = mock(RecommendationRuleSet.class);
        var mockRuleSet2 = mock(RecommendationRuleSet.class);

        var mockedRepository = mock(RecommendationsRepository.class);

        var dto1 = new RecommendationDto();
        when(mockRuleSet1.getRecommendation(eq(userId))).thenReturn(Optional.of(dto1));

        when(mockRuleSet2.getRecommendation(eq(userId))).thenReturn(Optional.empty());

        var service = new RecommendationService(List.of(mockRuleSet1, mockRuleSet2), mockedRepository);

        List<RecommendationDto> result = service.getRecommendations(userId);

        assertThat(result).containsExactly(dto1);
    }

    @Test
    void testGetDepAmountById_SuccessfulCase() {

        DepositTransactions expectedResult = new DepositTransactions(100, 200, 300, 400);
        when(repository.getDepositAmountByUserId(userId)).thenReturn(expectedResult);

        DepositTransactions actualResult = recommendationService.getDepAmountById(userId);

        assertNotNull(actualResult);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void testGetDepAmountById_NullCase() {

        when(repository.getDepositAmountByUserId(userId)).thenReturn(null);

        DepositTransactions actualResult = recommendationService.getDepAmountById(userId);

        assertNull(actualResult);
    }

    @Test
    void testGetWithdrawAmountById_SuccessfulCase() {

        WithdrawTransaction expectedResult = new WithdrawTransaction(100, 200, 300, 400);
        when(repository.getWithdrawAmountByUserId(userId)).thenReturn(expectedResult);

        WithdrawTransaction actualResult = recommendationService.getWithAmountById(userId);

        assertNotNull(actualResult);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void testGetWithdrawAmountById_NullCase() {

        when(repository.getWithdrawAmountByUserId(userId)).thenReturn(null);

        WithdrawTransaction actualResult = recommendationService.getWithAmountById(userId);

        assertNull(actualResult);
    }
}


