package br.com.pismo.challenge.transaction.infraestructure.config;

import br.com.pismo.challenge.transaction.domain.exception.DomainException;
import br.com.pismo.challenge.transaction.domain.exception.ExceptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class CustomerRestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CustomerRestExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionData> domainExceptionHandler(DomainException ex) {
        logger.info("domainExceptionHandler: {}: {}", ex.getClass().getSimpleName(), ex.getData());
        return ResponseEntity.status(ex.getStatus()).body(ex.getData());
    }

}
