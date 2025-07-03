package org.skypro.recommendationService.controller;

import org.junit.jupiter.api.DisplayName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.model.DepositTransactions;
import org.skypro.recommendationService.model.WithdrawTransaction;
import org.skypro.recommendationService.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    @DisplayName("GET /api/recommendations/{userId} returns recommendation response")
    void testGetRecommendations() throws Exception {
        UUID userId = UUID.randomUUID();

        RecommendationDto rec1 = new RecommendationDto(
                UUID.randomUUID(),
                "Sample Recommendation",
                "Description"
        );

        List<RecommendationDto> recommendations = List.of(rec1);
        when(recommendationService.getRecommendations(userId))
                .thenReturn(recommendations);

        mockMvc.perform(get("/api/recommendations/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", is(userId.toString())))
                .andExpect(jsonPath("$.recommendations", hasSize(1)))
                .andExpect(jsonPath("$.recommendations[0].name", is("Sample Recommendation")));
    }

    @Test
    public void testGetDepositById_ShouldReturnDepositTransactions() throws Exception {

        UUID userId = UUID.randomUUID();
        DepositTransactions expectedResult = new DepositTransactions(1000, 2000, 3000, 4000);
        given(recommendationService.getDepAmountById(userId)).willReturn(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendations/deposit/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    @Test
    public void testGetWithdrawById_ShouldReturnWithdrawTransactions() throws Exception {

        UUID userId = UUID.randomUUID();
        WithdrawTransaction expectedResult = new WithdrawTransaction(1000, 2000, 3000, 4000);
        given(recommendationService.getWithAmountById(userId)).willReturn(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendations/withdraw/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

}