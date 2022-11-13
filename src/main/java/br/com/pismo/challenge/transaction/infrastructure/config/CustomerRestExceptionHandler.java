package br.com.pismo.challenge.transaction.infrastructure.config;

import br.com.pismo.challenge.transaction.domain.exception.DomainException;
import br.com.pismo.challenge.transaction.domain.exception.ExceptionData;
import br.com.pismo.challenge.transaction.domain.exception.TypException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
public class CustomerRestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CustomerRestExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionData> domainExceptionHandler(DomainException ex) {
        logger.info("domainExceptionHandler: {}: {}", ex.getClass().getSimpleName(), ex.getData());
        return ResponseEntity.status(ex.getStatus()).body(ex.getData());
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionData> runtimeExceptionHandler(RuntimeException ex) {
        logger.info("runtimeExceptionHandler: Exception: {}, Message: {}", ex.getClass().getSimpleName(), ex.getMessage());
        final var data = new ExceptionData(TypException.INTERNAL_ERROR_SERVER, "Internal server error ! Please try again later!");
        return ResponseEntity.internalServerError().body(data);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("handleMethodArgumentNotValid: {}", ex.getMessage());
        final var error = ex.getBindingResult().getFieldErrors().stream();
        final var message = error.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("/"));
        return ResponseEntity.badRequest().body(new ExceptionData(TypException.INVALID_REQUEST, message));
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("handleExceptionInternal: {}:{}", ex.getClass().getSimpleName(), ex.getMessage());
        var data = new ExceptionData(TypException.INVALID_REQUEST, "Could not process this request!");
        return ResponseEntity.badRequest().body(data);
    }
}
