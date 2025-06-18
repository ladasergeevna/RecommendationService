package org.skypro.recommendationService.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationRepositoryTest {

    @Test
    @DisplayName("Should return true when user meets condition A")
    void testCheckUserConditionsA() {
        JdbcTemplate mockJdbc = mock(JdbcTemplate.class);
        RecommendationsRepository repo = new RecommendationsRepository(mockJdbc);
        UUID userId = UUID.randomUUID();

        when(mockJdbc.queryForObject(anyString(), eq(Integer.class), eq(userId))).thenReturn(1);

        boolean result = repo.checkUserConditionsA(userId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when user does not meet condition A")
    void testCheckUserConditionsA_False() {
        JdbcTemplate mockJdbc = mock(JdbcTemplate.class);
        RecommendationsRepository repo = new RecommendationsRepository(mockJdbc);
        UUID userId = UUID.randomUUID();

        when(mockJdbc.queryForObject(anyString(), eq(Integer.class), eq(userId))).thenReturn(0);

        boolean result = repo.checkUserConditionsA(userId);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true for condition B")
    void testCheckUserConditionsB() {
        JdbcTemplate mockJdbc = mock(JdbcTemplate.class);
        RecommendationsRepository repo = new RecommendationsRepository(mockJdbc);
        UUID userId = UUID.randomUUID();

        when(mockJdbc.queryForObject(anyString(), eq(Integer.class), eq(userId))).thenReturn(1);

        boolean result = repo.checkUserConditionsB(userId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false for condition C")
    void testCheckUserConditionsC_False() {
        JdbcTemplate mockJdbc = mock(JdbcTemplate.class);
        RecommendationsRepository repo = new RecommendationsRepository(mockJdbc);
        UUID userId = UUID.randomUUID();

        when(mockJdbc.queryForObject(anyString(), eq(Integer.class), eq(userId))).thenReturn(0);

        boolean result = repo.checkUserConditionsC(userId);
        assertFalse(result);
    }
}
