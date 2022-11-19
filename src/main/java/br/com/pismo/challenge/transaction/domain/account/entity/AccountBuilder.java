package br.com.pismo.challenge.transaction.domain.account.entity;

import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public final class AccountBuilder {
    private Account account;

    private AccountBuilder() {
        account = new Account();
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public AccountBuilder withId(UUID id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder withId(String id) {
        account.setId(UUID.fromString(id));
        return this;
    }

    public AccountBuilder withAgency(String agency) {
        account.setAgency(agency);
        return this;
    }

    public AccountBuilder withAccountNumber(String accountNumber) {
        account.setAccountNumber(accountNumber);
        return this;
    }

    public AccountBuilder withCodeBank(String codeBank) {
        account.setCodeBank(codeBank);
        return this;
    }

    public AccountBuilder withCustomer(Customer customer) {
        account.setCustomer(customer);
        return this;
    }

    public AccountBuilder withAccountBalance(BigDecimal accountBalance) {
        account.setAccountBalance(accountBalance);
        return this;
    }

    public AccountBuilder withCreateAt(Date createAt) {
        account.setCreateAt(createAt);
        return this;
    }

    public AccountBuilder withCreditLimit(BigDecimal value) {
        account.setCreditLimit(value);
        return this;
    }


    public AccountBuilder withTransactions(Set<Transaction> transactions) {
        account.setTransactions(transactions);
        return this;
    }

    public Account build() {
        return account;
    }
}
