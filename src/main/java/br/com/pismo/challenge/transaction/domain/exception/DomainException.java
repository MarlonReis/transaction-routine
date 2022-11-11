package br.com.pismo.challenge.transaction.domain.exception;


import org.springframework.http.HttpStatus;

public abstract class DomainException extends RuntimeException {
    private ExceptionData data;
    private HttpStatus status;

    public DomainException(HttpStatus status, TypException type, String message) {
        this.data = new ExceptionData(type, message);
        this.status = status;
    }

    public final HttpStatus getStatus() {
        return status;
    }

    public final ExceptionData getData() {
        return data;
    }
}
