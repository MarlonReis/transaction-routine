package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.WITHDRAW_MONEY_RECEIVED_INVALID_VALUE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class WithdrawMoneyException extends DomainException {
    private WithdrawMoneyException(HttpStatus status, TypException type, String message) {
        super(status, type, message);
    }

    public static WithdrawMoneyException receiveInvalidValue(String message) {
        return new WithdrawMoneyException(UNPROCESSABLE_ENTITY, WITHDRAW_MONEY_RECEIVED_INVALID_VALUE, message);
    }
}
