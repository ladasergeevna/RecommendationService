package org.skypro.recommendationService.dto;

import java.util.List;

public class RecommendationRuleDto {
    private String product_name;
    private String product_text;
    private List<RuleDto> rule;

    public RecommendationRuleDto(String product_name, String product_text, List<RuleDto> rule) {
        this.product_name = product_name;
        this.product_text = product_text;
        this.rule = rule;
    }

    public List<RuleDto> getRule() {
        return rule;
    }

    public void setRule(List<RuleDto> rule) {
        this.rule = rule;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString() {
        return "RecommendationRuleDto{" +
                "product_name='" + product_name + '\'' +
                ", product_text='" + product_text + '\'' +
                ", rule=" + rule +
                '}';
    }
}
