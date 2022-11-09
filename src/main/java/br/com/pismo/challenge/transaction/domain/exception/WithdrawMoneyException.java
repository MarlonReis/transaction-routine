package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.WITHDRAW_MONEY_RECEIVED_INVALID_VALUE;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class WithdrawMoneyException extends DomainException {
    public WithdrawMoneyException(String message) {
        super(WITHDRAW_MONEY_RECEIVED_INVALID_VALUE, message);
    }
}
