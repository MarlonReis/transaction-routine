package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PurchaseTransactionException extends DomainException {
    public PurchaseTransactionException(String message) {
        super(PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA, message);
    }

}
