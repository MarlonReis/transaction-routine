package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;
import static br.com.pismo.challenge.transaction.domain.exception.TypException.CANNOT_CREATE_ACCOUNT;
import static br.com.pismo.challenge.transaction.domain.exception.TypException.DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class AccountException extends DomainException {

    private AccountException(HttpStatus status, TypException type, String message) {
        super(status, type, message);
    }

    public static AccountException cannotCreateAccount(String message) {
        return new AccountException(INTERNAL_SERVER_ERROR, CANNOT_CREATE_ACCOUNT, message);
    }

    public static AccountException documentIsBeUsedByAnotherAccount(String message) {
        return new AccountException(UNPROCESSABLE_ENTITY, DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT, message);
    }
}
