package br.com.pismo.challenge.transaction.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidDocumentException extends DomainException {
    public InvalidDocumentException(String message) {
        super(TypException.DOCUMENT_IS_INVALID, message);
    }

    public InvalidDocumentException() {
        super(TypException.DOCUMENT_IS_INVALID, "Document invalid!");
    }
}
