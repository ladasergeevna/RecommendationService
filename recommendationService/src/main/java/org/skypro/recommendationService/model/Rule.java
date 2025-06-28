package org.skypro.recommendationService.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "query")
    private String query;

    @ElementCollection
    @CollectionTable(name = "rule_arguments", joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "argument")
    private List<String> arguments;

    @Column(name = "negate")
    private boolean negate;

    @ManyToOne
    @JoinColumn(name = "productId")
    private RecommendationsByRules recommendation;

    public Rule() {
    }

    public Rule(String query, List<String> arguments, boolean negate, RecommendationsByRules recommendation) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
        this.recommendation = recommendation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        if (!(o instanceof Rule)) return false;
        Rule rule = (Rule) o;
        return id != null && id.equals(rule.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
