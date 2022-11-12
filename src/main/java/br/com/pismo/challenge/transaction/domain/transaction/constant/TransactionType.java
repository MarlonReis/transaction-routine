package br.com.pismo.challenge.transaction.domain.transaction.constant;

import br.com.pismo.challenge.transaction.domain.exception.TransactionException;

import java.util.stream.Stream;

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

    public static TransactionType byCode(int code) {
        return Stream.of(values()).filter(it -> it.getCode() == code).findFirst()
                .orElseThrow(TransactionException::invalidTransaction);
    }

    public int getCode() {
        return code;
    }


    @Override
    public String toString() {
        return this.name();
    }
}
