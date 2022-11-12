package br.com.pismo.challenge.transaction.infraestructure.service;

import br.com.pismo.challenge.transaction.domain.account.entity.AccountBuilder;
import br.com.pismo.challenge.transaction.domain.account.service.AccountService;
import br.com.pismo.challenge.transaction.domain.shared.IdEntity;
import br.com.pismo.challenge.transaction.domain.transaction.boundary.input.SaveTransactionInputBoundary;
import br.com.pismo.challenge.transaction.domain.transaction.constant.TransactionType;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;
import br.com.pismo.challenge.transaction.domain.transaction.service.TransactionService;
import br.com.pismo.challenge.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImp implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionServiceImp(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public void saveTransaction(SaveTransactionInputBoundary data) {
        final var transactionType = TransactionType.byCode(data.getOperationTypeId());

        final var account = accountService.getAccountById(new IdEntity(data.getAccountId()));
        final var transaction = new Transaction(AccountBuilder.builder().withId(account.getAccountId()).build());

        if (transactionType.isPurchaseTransaction()) {
            transaction.purchaseTransaction(data.getAmount(), transactionType);
        } else if (transactionType == TransactionType.WITHDRAWAL_MONEY) {
            transaction.withdrawMoney(data.getAmount());
        } else {
            transaction.payment(data.getAmount());
        }

        transactionRepository.save(transaction);

    }
}
