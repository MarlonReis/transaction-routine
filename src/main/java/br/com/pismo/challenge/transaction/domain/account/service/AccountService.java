package br.com.pismo.challenge.transaction.domain.account.service;

import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.CreateAccountOutputBoundary;

public interface AccountService {
    CreateAccountOutputBoundary createAccount(CreateAccountInputBoundary account);
}
