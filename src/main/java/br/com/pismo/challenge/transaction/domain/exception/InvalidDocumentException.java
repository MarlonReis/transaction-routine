package br.com.pismo.challenge.transaction.domain.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class InvalidDocumentException extends DomainException {
    public InvalidDocumentException(String message) {
        super(UNPROCESSABLE_ENTITY, TypException.DOCUMENT_IS_INVALID, message);
    }

    public InvalidDocumentException() {
        super(UNPROCESSABLE_ENTITY, TypException.DOCUMENT_IS_INVALID, "Document invalid!");
    }
}
