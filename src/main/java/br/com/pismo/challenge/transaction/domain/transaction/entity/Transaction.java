package br.com.pismo.challenge.transaction.domain.transaction.entity;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.exception.PaymentException;
import br.com.pismo.challenge.transaction.domain.exception.PurchaseTransactionException;
import br.com.pismo.challenge.transaction.domain.exception.WithdrawMoneyException;
import br.com.pismo.challenge.transaction.domain.transaction.constant.TransactionType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "index_transaction_id", columnList = "id"),
        @Index(name = "index_transaction_create_at", columnList = "createAt"),
        @Index(name = "index_transaction_customer_id", columnList = "customer_id"),
        @Index(name = "index_transaction_account_id", columnList = "account_id")
}
)
public class Transaction {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column
    private BigDecimal value;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public Account account;


    public Transaction(Customer customer) {
        this.customer = customer;
        this.id = UUID.randomUUID();
        this.createAt = new Date();
    }

    public Transaction() {
    }

    public void purchaseTransaction(BigDecimal value, TransactionType type) {
        if (value == null) {
            throw new PurchaseTransactionException("Purchase transaction value is required!");
        }

        if (value.signum() == -1) {
            throw new PurchaseTransactionException("Purchase transaction value receive negative value!");
        }

        if (value.signum() == 0) {
            throw new PurchaseTransactionException("Purchase transaction value receive invalid value!");
        }
        if (type == null) {
            throw new PurchaseTransactionException("Purchase transaction type is required!");
        }

        if (!type.isPurchaseTransaction()) {
            throw new PurchaseTransactionException("Purchase transaction type '" + type.name() + "' is invalid!");
        }

        this.value = value.negate();
        this.type = type;
    }

    public void withdrawMoney(BigDecimal value) {
        if (value == null) {
            throw new WithdrawMoneyException("Withdraw money value is required!");
        }
        if (value.signum() < 1) {
            throw new WithdrawMoneyException("Withdraw money value must be greater than zero!");
        }
        if (value.toPlainString().matches(".+[1-9]+")) {
            throw new WithdrawMoneyException("The withdrawal amount must be a whole number!");
        }

        this.value = value.negate();
        this.type = TransactionType.WITHDRAWAL_MONEY;
    }

    public void payment(BigDecimal value) {
        if (value == null) {
            throw PaymentException.createReceivedInvalidValue("payment value is required!");
        }
        if (value.signum() < 1) {
            throw PaymentException.createReceivedInvalidValue("payment value must be greater than zero!");
        }

        this.value = value;
        this.type = TransactionType.PAYMENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Transaction) {
            Transaction that = (Transaction) o;
            return getId().equals(that.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public UUID getId() {
        return id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public TransactionType getType() {
        return type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Account getAccount() {
        return account;
    }
}
