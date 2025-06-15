package org.skypro.recommendationService.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skypro.recommendationService.dto.RecommendationDto;
import org.skypro.recommendationService.dto.RecommendationResponse;
import org.skypro.recommendationService.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}
