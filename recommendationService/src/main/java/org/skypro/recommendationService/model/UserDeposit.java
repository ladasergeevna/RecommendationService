package org.skypro.recommendationService.model;

import java.util.Objects;
import java.util.UUID;

public class UserDeposit {
    private UUID userId;
    private int debitDeposit;
    private int creditDeposit;
    private int investDeposit;
    private int saveDeposit;

    public UserDeposit(UUID userId, int debitDeposit, int creditDeposit, int investDeposit, int saveDeposit) {
        this.userId = userId;
        this.debitDeposit = debitDeposit;
        this.creditDeposit = creditDeposit;
        this.investDeposit = investDeposit;
        this.saveDeposit = saveDeposit;
    }

    public UUID getUserId() {
        return userId;
    }

    public int getDebitDeposit() {
        return debitDeposit;
    }

    public int getCreditDeposit() {
        return creditDeposit;
    }

    public int getInvestDeposit() {
        return investDeposit;
    }

    public int getSaveDeposit() {
        return saveDeposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDeposit that = (UserDeposit) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
