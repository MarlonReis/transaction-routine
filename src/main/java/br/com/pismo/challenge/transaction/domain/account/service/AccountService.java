package br.com.pismo.challenge.transaction.domain.account.service;

import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.CreateAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.GetAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.shared.IdEntity;

public interface AccountService {
    CreateAccountOutputBoundary createAccount(CreateAccountInputBoundary account);

    GetAccountOutputBoundary getAccountById(IdEntity id);
}
