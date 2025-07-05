package org.skypro.recommendationService.component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class RuleStats {

    @Id
    private UUID ruleId;

    @Column(nullable = false)
    private int count;

    public RuleStats() {
    }

    public RuleStats(UUID ruleId) {
    }


    public UUID getRuleId() {
        return ruleId;
    }

    public void setRuleId(UUID ruleId) {
        this.ruleId = ruleId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        this.count++;
    }
    public void reset() {
        this.count = 0;
    }
}