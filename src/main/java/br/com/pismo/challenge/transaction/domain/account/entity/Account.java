package br.com.pismo.challenge.transaction.domain.account.entity;


import br.com.pismo.challenge.transaction.domain.account.value.object.CreditLimit;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.exception.AccountException;
import br.com.pismo.challenge.transaction.domain.exception.LimitException;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(indexes = {@Index(name = "index_account_id", columnList = "id"), @Index(name = "index_account_account_number", columnList = "accountNumber"), @Index(name = "index_account_create_at", columnList = "createAt"), @Index(name = "index_account_customer_id", columnList = "customer_id")}, uniqueConstraints = {@UniqueConstraint(name = "UniqueAccountNumber", columnNames = {"accountNumber"})})
public class Account implements Serializable {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String agency;
    private String accountNumber;
    private String codeBank;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private BigDecimal accountBalance;

    private BigDecimal creditLimit;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;


    public Account(String agency, String accountNumber, String codeBank, BigDecimal accountBalance, Customer customer, CreditLimit limit) {
        this.agency = agency;
        this.accountNumber = accountNumber;
        this.codeBank = codeBank;
        this.accountBalance = accountBalance;
        this.customer = customer;
        this.creditLimit = limit.getValue();
    }

    public Account() {
    }

    public void updateAccountBalance(BigDecimal value) {
        if (value == null) {
            throw AccountException.balanceValueIsRequired("Balance value is required!");
        }
        if (value.signum() == -1) {
            final var max = accountBalance.plus().add(creditLimit);
            if (max.plus().add(value).signum() < 0) {
                throw LimitException.accountLimitInsufficient("The customer account doesn't has limit to this transaction!");
            }
        }

        accountBalance = accountBalance.plus().add(value);
    }


    @PrePersist
    private void prePersist() {
        this.createAt = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getAgency() {
        return agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCodeBank() {
        return codeBank;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    protected void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    protected void setAgency(String agency) {
        this.agency = agency;
    }

    protected void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    protected void setCodeBank(String codeBank) {
        this.codeBank = codeBank;
    }

    protected void setCustomer(Customer customer) {
        this.customer = customer;
    }

    protected void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    protected void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    protected void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", agency='" + agency + '\'' + ", accountNumber='" + accountNumber + '\'' + ", codeBank='" + codeBank + '\'' + ", customer=" + customer + ", accountBalance=" + accountBalance + ", createAt=" + createAt + '}';
    }
}
