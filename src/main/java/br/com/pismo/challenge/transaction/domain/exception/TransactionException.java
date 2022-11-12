package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.UNKNOWN_TRANSACTION;

public class TransactionException extends DomainException {
    private TransactionException(HttpStatus status, TypException type, String message) {
        super(status, type, message);
    }

    public static TransactionException invalidTransaction() {
        return new TransactionException(HttpStatus.UNPROCESSABLE_ENTITY, UNKNOWN_TRANSACTION, "Transaction is unknown, cannot be processed!");
    }

}
