package br.com.pismo.challenge.transaction.infrastructure.service;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.exception.AccountException;
import br.com.pismo.challenge.transaction.domain.shared.value.object.IdEntity;
import br.com.pismo.challenge.transaction.domain.transaction.boundary.input.SaveTransactionInputBoundary;
import br.com.pismo.challenge.transaction.domain.transaction.constant.TransactionType;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;
import br.com.pismo.challenge.transaction.domain.transaction.service.TransactionService;
import br.com.pismo.challenge.transaction.repository.AccountRepository;
import br.com.pismo.challenge.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImp implements TransactionService {
    private final TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImp(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public void saveTransaction(SaveTransactionInputBoundary data) {
        final var transactionType = TransactionType.byCode(data.getOperationTypeId());
        Account account = accountRepository.findById(new IdEntity(data.getAccountId()).value()).
                orElseThrow(() -> AccountException.accountNotFound("Account register cannot be found!"));
        final var transaction = new Transaction(account);


        if (transactionType.isPurchaseTransaction()) {
            transaction.purchaseTransaction(data.getAmount(), transactionType);
        } else if (transactionType == TransactionType.WITHDRAWAL_MONEY) {
            transaction.withdrawMoney(data.getAmount());
        } else {
            transaction.payment(data.getAmount());
        }

        account.updateAccountBalance(transaction.getValue());
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }
}
