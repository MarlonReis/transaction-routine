package br.com.pismo.challenge.transaction.domain.customer.entity;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public final class CustomerBuilder {
    private Customer customer;

    private CustomerBuilder() {
        customer = new Customer();
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public CustomerBuilder withId(UUID id) {
        customer.setId(id);
        return this;
    }

    public CustomerBuilder withFullName(String fullName) {
        customer.setFullName(fullName);
        return this;
    }

    public CustomerBuilder withDocument(String document) {
        customer.setDocument(document);
        return this;
    }

    public CustomerBuilder withAccount(Account account) {
        customer.setAccount(account);
        return this;
    }

    public CustomerBuilder withCreateAt(Date createAt) {
        customer.setCreateAt(createAt);
        return this;
    }

    
    public Customer build() {
        return customer;
    }
}
