package br.com.pismo.challenge.transaction.infraestructure.controller;

import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.CreateAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.GetAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.account.service.AccountService;
import br.com.pismo.challenge.transaction.domain.shared.IdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping("/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAccountOutputBoundary> createAccount(@Valid @RequestBody CreateAccountInputBoundary data) {
        var response = accountService.createAccount(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountOutputBoundary> getAccountById(@PathVariable("accountId") String id) {
        final var response = accountService.getAccountById(new IdEntity(id));
        return ResponseEntity.ok(response);
    }

}
