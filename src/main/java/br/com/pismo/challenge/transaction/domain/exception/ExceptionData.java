package br.com.pismo.challenge.transaction.domain.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ExceptionData {
    private TypException code;
    private String message;

    private String date;

    public ExceptionData(TypException code, String message) {
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now().toString();
    }

    public TypException getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "{ " +
                "code:" + code +
                ", message:'" + message + '\'' +
                ", date:'" + date + '\'' +
                " }";
    }
}
