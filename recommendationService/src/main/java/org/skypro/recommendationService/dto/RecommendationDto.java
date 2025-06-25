package org.skypro.recommendationService.dto;

import java.util.UUID;

/**
 * DTO-класс для представления одной рекомендации.
 * Содержит идентификатор, название и текст рекомендации.
 */
public class RecommendationDto {
    private UUID id;
    private String name;
    private String text;

    public RecommendationDto(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public RecommendationDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
