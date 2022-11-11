package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.PortUnreachableException;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.CANNOT_CREATE_ACCOUNT;
import static br.com.pismo.challenge.transaction.domain.exception.TypException.DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class AccountException extends DomainException {

    private AccountException(TypException type, String message) {
        super(type, message);
    }

    public static AccountException cannotCreateAccount(String message) {
        return new AccountException(CANNOT_CREATE_ACCOUNT, message);
    }

    public static AccountException documentIsBeUsedByAnotherAccount(String message) {
        return new AccountException(DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT, message);
    }
}
