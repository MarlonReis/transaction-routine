package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ResponseStatus(UNPROCESSABLE_ENTITY)
public class PaymentException extends DomainException {
    public PaymentException(TypException type, String message) {
        super(UNPROCESSABLE_ENTITY, type, message);
    }

    public static PaymentException receivedInvalidValue(String message) {
        return new PaymentException(TypException.PAYMENT_RECEIVED_INVALID_VALUE, message);
    }
}
