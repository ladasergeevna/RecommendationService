package org.skypro.recommendationService.model;

public class WithdrawTransaction {
    private int debitAmount;
    private int savingAmount;
    private int creditAmount;
    private int investAmount;

    public WithdrawTransaction(int debitAmount, int savingAmount, int creditAmount, int investAmount) {
        this.debitAmount = debitAmount;
        this.savingAmount = savingAmount;
        this.creditAmount = creditAmount;
        this.investAmount = investAmount;
    }

    public int getDebitAmount() {
        return debitAmount;
    }

    public int getSavingAmount() {
        return savingAmount;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public int getInvestAmount() {
        return investAmount;
    }
}
