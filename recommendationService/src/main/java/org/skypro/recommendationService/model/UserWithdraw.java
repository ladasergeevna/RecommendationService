package org.skypro.recommendationService.model;

import java.util.Objects;
import java.util.UUID;

public class UserWithdraw {
    private UUID userId;
    private int debitWithdraw;
    private int creditWithdraw;
    private int investWithdraw;
    private int saveWithdraw;

    public UserWithdraw(UUID userId, int debitWithdraw, int creditWithdraw, int investWithdraw, int saveWithdraw) {
        this.userId = userId;
        this.debitWithdraw = debitWithdraw;
        this.creditWithdraw = creditWithdraw;
        this.investWithdraw = investWithdraw;
        this.saveWithdraw = saveWithdraw;
    }

    public UUID getUserId() {
        return userId;
    }

    public int getDebitWithdraw() {
        return debitWithdraw;
    }

    public int getCreditWithdraw() {
        return creditWithdraw;
    }

    public int getInvestWithdraw() {
        return investWithdraw;
    }

    public int getSaveWithdraw() {
        return saveWithdraw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWithdraw that = (UserWithdraw) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
