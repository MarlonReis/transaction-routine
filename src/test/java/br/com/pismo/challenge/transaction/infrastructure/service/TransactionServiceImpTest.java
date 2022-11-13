package br.com.pismo.challenge.transaction.infrastructure.service;

import br.com.pismo.challenge.transaction.domain.account.boundary.output.GetAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.account.service.AccountService;
import br.com.pismo.challenge.transaction.domain.transaction.boundary.input.SaveTransactionInputBoundary;
import br.com.pismo.challenge.transaction.domain.transaction.constant.TransactionType;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;
import br.com.pismo.challenge.transaction.repository.TransactionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImpTest {

    @Spy
    private TransactionRepository transactionRepository;

    @Spy
    private AccountService accountService;

    @InjectMocks
    private TransactionServiceImp transactionService;

    private GetAccountOutputBoundary account;

    @BeforeEach
    void setUp() {
        final var id = "5ab2e4ed-211b-4c7d-92c2-babfa354caf6";
        final var document = "98641382003";
        account = new GetAccountOutputBoundary(id, document);
    }


    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"CASH_PURCHASE", "INSTALLMENT_PURCHASE", "WITHDRAWAL_MONEY", "PAYMENT"})
    @DisplayName("should save different type os transactions")
    void shouldSaveDifferentTypeOsTransaction(TransactionType type) {
        when(accountService.getAccountById(Mockito.any())).thenReturn(account);
        final var transaction = new SaveTransactionInputBoundary(
                account.getAccountId(),
                type.getCode(),
                BigDecimal.valueOf(10.00)
        );

        transactionService.saveTransaction(transaction);
        ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository).save(argument.capture());

        final var arg = argument.getValue();

        assertThat(arg.getValue(), Matchers.notNullValue());
        assertThat(arg.getAccount().getId(), Matchers.is(UUID.fromString(account.getAccountId())));
        assertThat(arg.getType(), Matchers.is(type));
    }

}