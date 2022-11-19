package br.com.pismo.challenge.transaction.domain.account.entity;

import br.com.pismo.challenge.transaction.domain.account.value.object.CreditLimit;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.exception.AccountException;
import br.com.pismo.challenge.transaction.domain.exception.LimitException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.ACCOUNT_BALANCE_IS_REQUIRED;
import static br.com.pismo.challenge.transaction.domain.exception.TypException.ACCOUNT_LIMIT_IS_INSUFFICIENT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("232", "423423", "1",
                BigDecimal.valueOf(1000), new Customer(),
                new CreditLimit(BigDecimal.valueOf(1000)));
    }


    @Test
    @DisplayName("should throw LimitException when account over credit limit")
    void shouldThrowLimitExceptionWhenAccountOverCreditLimit() {
        final var response = assertThrows(LimitException.class,
                () -> account.updateAccountBalance(BigDecimal.valueOf(-2001)));

        assertThat(response.getData().getCode(), CoreMatchers.is(ACCOUNT_LIMIT_IS_INSUFFICIENT));
    }

    @Test
    @DisplayName("should throw AccountException when value is null")
    void shouldThrowAccountExceptionWhenValueIsNull() {
        final var response = assertThrows(AccountException.class,
                () -> account.updateAccountBalance(null));

        assertThat(response.getData().getCode(), CoreMatchers.is(ACCOUNT_BALANCE_IS_REQUIRED));
    }

    @Test
    @DisplayName("should return account with balance")
    void shouldDiscountOneHundredAndRest900OfAccountBalance() {
        account.updateAccountBalance(BigDecimal.valueOf(-100));
        assertThat(account.getAccountBalance(), CoreMatchers.is(new BigDecimal(900)));
    }

    @Test
    @DisplayName("should return account with balance")
    void shouldIncreaseOneHundredAndTotalAmountIs1100OfAccountBalance() {
        account.updateAccountBalance(BigDecimal.valueOf(100));
        assertThat(account.getAccountBalance(), CoreMatchers.is(new BigDecimal(1100)));
    }
}