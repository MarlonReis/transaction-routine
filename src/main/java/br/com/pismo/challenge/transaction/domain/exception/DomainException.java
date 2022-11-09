package br.com.pismo.challenge.transaction.domain.exception;


public abstract class DomainException extends RuntimeException {
    private ExceptionData data;

    public DomainException(TypException type, String message) {
        this.data = new ExceptionData(type, message);
    }

    public ExceptionData getData() {
        return data;
    }
}
