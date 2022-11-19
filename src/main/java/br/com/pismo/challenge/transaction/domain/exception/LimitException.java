package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.*;

public class LimitException extends DomainException {

    private LimitException(HttpStatus status, TypException type, String message) {
        super(status, type, message);
    }

    public static LimitException cannotBeNegative(String message) {
        return new LimitException(HttpStatus.UNPROCESSABLE_ENTITY, CREDIT_LIMIT_CANNOT_BE_NEGATIVE, message);
    }

    public static LimitException valueIsRequired(String message) {
        return new LimitException(HttpStatus.UNPROCESSABLE_ENTITY, CREDIT_LIMIT_IS_REQUIRED, message);
    }

    public static LimitException accountLimitInsufficient(String message) {
        return new LimitException(HttpStatus.UNPROCESSABLE_ENTITY, ACCOUNT_LIMIT_IS_INSUFFICIENT, message);
    }


}
