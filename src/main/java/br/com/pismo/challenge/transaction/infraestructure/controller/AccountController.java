package br.com.pismo.challenge.transaction.infraestructure.controller;

import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.CreateAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAccountOutputBoundary> createAccount(@RequestBody CreateAccountInputBoundary data) {
        var response = accountService.createAccount(data);
        return ResponseEntity.ok(response);
    }

}
