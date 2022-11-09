package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PaymentException extends DomainException {
    public PaymentException(String message) {
        super(TypException.PAYMENT_RECEIVED_INVALID_VALUE, message);
    }
}
