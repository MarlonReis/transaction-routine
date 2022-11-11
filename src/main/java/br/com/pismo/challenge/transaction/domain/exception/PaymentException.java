package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PaymentException extends DomainException {
    public PaymentException(TypException type, String message) {
        super(type, message);
    }

    public static PaymentException createReceivedInvalidValue(String message) {
        return new PaymentException(TypException.PAYMENT_RECEIVED_INVALID_VALUE, message);
    }
}
