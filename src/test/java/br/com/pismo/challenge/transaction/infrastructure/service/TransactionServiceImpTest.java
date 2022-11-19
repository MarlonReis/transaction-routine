package br.com.pismo.challenge.transaction.infrastructure.service;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.account.entity.AccountBuilder;
import br.com.pismo.challenge.transaction.domain.exception.AccountException;
import br.com.pismo.challenge.transaction.domain.exception.IdentityException;
import br.com.pismo.challenge.transaction.domain.exception.LimitException;
import br.com.pismo.challenge.transaction.domain.transaction.boundary.input.SaveTransactionInputBoundary;
import br.com.pismo.challenge.transaction.domain.transaction.constant.TransactionType;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;
import br.com.pismo.challenge.transaction.repository.AccountRepository;
import br.com.pismo.challenge.transaction.repository.TransactionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImpTest {

    @Spy
    private TransactionRepository transactionRepository;

    @Spy
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImp transactionService;

    private Account account;

    @BeforeEach
    void setUp() {
        final var id = "5ab2e4ed-211b-4c7d-92c2-babfa354caf6";

        account = AccountBuilder.builder().withId(id).withAgency("232")
                .withAccountNumber("423423").withCodeBank("1")
                .withAccountBalance(new BigDecimal(1000))
                .withCreateAt(new Date())
                .withCreditLimit(new BigDecimal(1000))
                .build();
    }


    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"CASH_PURCHASE", "INSTALLMENT_PURCHASE", "WITHDRAWAL_MONEY", "PAYMENT"})
    @DisplayName("should save different type os transactions")
    void shouldSaveDifferentTypeOsTransaction(TransactionType type) {
        when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));

        final var transaction = createInput(account.getId().toString(), type, 10);

        transactionService.saveTransaction(transaction);
        ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository).save(argument.capture());

        final var arg = argument.getValue();

        assertThat(arg.getValue(), Matchers.notNullValue());
        assertThat(arg.getAccount().getId(), Matchers.is(account.getId()));
        assertThat(arg.getType(), Matchers.is(type));
    }

    @Test
    @DisplayName("should throws AccountException when account repository not found")
    void shouldThrowAccountExceptionWhenNotFoundAccount() {
        when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var transaction = createInput(account.getId().toString(), TransactionType.CASH_PURCHASE, 10);

        assertThrows(AccountException.class, () -> transactionService.saveTransaction(transaction));
    }

    @Test
    @DisplayName("should throw IdentityException when receive invalid id")
    void shouldThrowIdentityExceptionWhenReceiveInvalidIdentity() {
        final var transaction = createInput("22945a6e-f115-4beb-8cc3",
                TransactionType.CASH_PURCHASE, 10);

        assertThrows(IdentityException.class, () -> transactionService.saveTransaction(transaction));
    }

    @Test
    @DisplayName("should throws LimitException when transaction over account credit limit")
    void shouldThrowLimitExceptionWhenTransactionOverAccountCreditLimit() {
        when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        final var transaction = createInput(account.getId().toString(), TransactionType.CASH_PURCHASE, 2100);
        assertThrows(LimitException.class, () -> transactionService.saveTransaction(transaction));
    }




    private SaveTransactionInputBoundary createInput(String accountId, TransactionType type, double value) {
        return new SaveTransactionInputBoundary(accountId, type.getCode(), new BigDecimal(value));
    }

}