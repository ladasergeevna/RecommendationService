package org.skypro.recommendationService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecommendationRuleDto {
    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productText")
    private String productText;

    private List<RuleDto> rule;

    public RecommendationRuleDto(String productName, String productText, List<RuleDto> rule) {
        this.productName = productName;
        this.productText = productText;
        this.rule = rule;
    }

    public RecommendationRuleDto() {}

    public List<RuleDto> getRule() {
        return rule;
    }

    public void setRule(List<RuleDto> rule) {
        this.rule = rule;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "RecommendationRuleDto{" +
                "product_name='" + productName + '\'' +
                ", product_text='" + productText + '\'' +
                ", rule=" + rule +
                '}';
    }
}
