package org.skypro.recommendationService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.skypro.recommendationService.controller.RecommendationRuleController;
import org.skypro.recommendationService.dto.RecommendationRuleDto;
import org.skypro.recommendationService.model.RecommendationsByRules;
import org.skypro.recommendationService.service.RecommendationByRulesService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(RecommendationRuleController.class)
class RecommendationRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationByRulesService recsByRulesService;

    private final UUID userId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        reset(recsByRulesService);
    }

    @Test
    public void testSaveRecs_Success() throws Exception {

        RecommendationRuleDto dto = new RecommendationRuleDto();
        RecommendationsByRules savedObj = new RecommendationsByRules();
        when(recsByRulesService.saveRecByRule(any(RecommendationRuleDto.class))).thenReturn(savedObj);

        mockMvc.perform(MockMvcRequestBuilders.post("/recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    public void testGetRecForUser_Found() throws Exception {

        List<RecommendationsByRules> list = Collections.singletonList(new RecommendationsByRules());
        when(recsByRulesService.selectRecommendation(userId)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/recommendation/user/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetRecForUser_NotFound() throws Exception {

        when(recsByRulesService.selectRecommendation(userId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/recommendation/user/" + userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteRecommendationByRule_Success() throws Exception {

        doNothing().when(recsByRulesService).deleteRecommendationByProductId(productId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/recommendation/" + productId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testClearAll_Success() throws Exception {

        doNothing().when(recsByRulesService).deleteRules();

        mockMvc.perform(MockMvcRequestBuilders.get("/recommendation/clear-all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Вспомогательная функция для превращения объекта в JSON
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}