package org.skypro.recommendationService.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "query")
    private String query;
    @Column(name = "arguments")
    private List<String> arguments;
    @Column(name = "negate")
    private boolean negate;

    @ManyToOne
    @JoinColumn(name = "productId")
    private RecommendationsByRules recommendation;

    public Rule(String query, List<String> arguments, boolean negate, RecommendationsByRules recommendation) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
        this.recommendation = recommendation;
    }

    public String getQuery() {
        return query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setRecommendation(RecommendationsByRules recommendation) {
        this.recommendation = recommendation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(id, rule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
