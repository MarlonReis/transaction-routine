package br.com.pismo.challenge.transaction.domain.exception;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class PurchaseTransactionException extends DomainException {
    public PurchaseTransactionException(String message) {
        super(UNPROCESSABLE_ENTITY, PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA, message);
    }

}
