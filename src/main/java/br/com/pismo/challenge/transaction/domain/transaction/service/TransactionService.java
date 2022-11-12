package br.com.pismo.challenge.transaction.domain.transaction.service;

import br.com.pismo.challenge.transaction.domain.transaction.boundary.input.SaveTransactionInputBoundary;

public interface TransactionService {
    void saveTransaction(SaveTransactionInputBoundary data);
}
