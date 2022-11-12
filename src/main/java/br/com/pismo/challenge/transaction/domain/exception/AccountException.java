package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.*;
import static org.springframework.http.HttpStatus.*;

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

    public static AccountException accountNotFound(String message) {
        return new AccountException(NOT_FOUND, REGISTER_NOT_FOUND, message);
    }

}
