package br.com.pismo.challenge.transaction.infrastructure.controller;

import br.com.pismo.challenge.transaction.domain.transaction.boundary.input.SaveTransactionInputBoundary;
import br.com.pismo.challenge.transaction.domain.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTransaction(@Valid @RequestBody SaveTransactionInputBoundary data) {
        transactionService.saveTransaction(data);
        return ResponseEntity.ok().build();
    }

}
