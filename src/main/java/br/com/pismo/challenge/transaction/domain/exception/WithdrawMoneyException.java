package br.com.pismo.challenge.transaction.domain.exception;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.WITHDRAW_MONEY_RECEIVED_INVALID_VALUE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class WithdrawMoneyException extends DomainException {
    public WithdrawMoneyException(String message) {
        super(UNPROCESSABLE_ENTITY, WITHDRAW_MONEY_RECEIVED_INVALID_VALUE, message);
    }
}
