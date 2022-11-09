package br.com.pismo.challenge.transaction.domain.transaction.constant;

public enum TransactionType {
    CASH_PURCHASE(1),
    INSTALLMENT_PURCHASE(2),
    WITHDRAWAL_MONEY(3),
    PAYMENT(4);

    private final int code;

    TransactionType(int code) {
        this.code = code;
    }

    public boolean isPurchaseTransaction() {
        return this == CASH_PURCHASE || this == INSTALLMENT_PURCHASE;
    }

    public int getCode() {
        return code;
    }
}
