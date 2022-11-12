package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.IDENTITY_VALUE_IS_INVALID;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class IdentityException extends DomainException {
    private IdentityException(HttpStatus status, TypException type, String message) {
        super(status, type, message);
    }

    public static IdentityException invalidValue(String message) {
        return new IdentityException(UNPROCESSABLE_ENTITY, IDENTITY_VALUE_IS_INVALID, message);
    }
}
