package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class PurchaseTransactionException extends DomainException {

    private PurchaseTransactionException(HttpStatus status, TypException type, String message) {
        super(status, type, message);
    }

    public static PurchaseTransactionException receiveInvalidValue(String message) {
        return new PurchaseTransactionException(UNPROCESSABLE_ENTITY, PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA, message);
    }

}
