package br.com.pismo.challenge.transaction.domain.account.value.object;

import br.com.pismo.challenge.transaction.domain.exception.LimitException;

import java.math.BigDecimal;

public class CreditLimit {
    private final BigDecimal value;


    public CreditLimit(BigDecimal value) {
        this.value = value;
        if (value == null) {
            throw LimitException.valueIsRequired("Credit limit is required!");
        }
        if (value.signum() == -1) {
            throw LimitException.cannotBeNegative("Credit limit cannot be negative!");
        }
    }

    public BigDecimal getValue() {
        return value;
    }
}
