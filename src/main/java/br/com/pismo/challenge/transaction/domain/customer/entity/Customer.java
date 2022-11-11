package br.com.pismo.challenge.transaction.domain.customer.entity;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.customer.value.object.DocumentCPF;
import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "index_custom_id", columnList = "id"),
        @Index(name = "index_custom_document", columnList = "document"),
        @Index(name = "index_custom_create_at", columnList = "createAt")
},
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueCustomerDocument", columnNames = {"document"})
        }
)
public class Customer {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String fullName;

    private String document;

    @OneToOne(mappedBy = "customer")
    private Account account;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @OneToMany(mappedBy = "customer")
    private Set<Transaction> transactions;

    public Customer() {
    }

    public Customer(DocumentCPF documentCpf, String fullName) {
        this.document = documentCpf.value();
        this.fullName = fullName;
    }


    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocument() {
        return document;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Account getAccount() {
        return account;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    protected void setFullName(String fullName) {
        this.fullName = fullName;
    }

    protected void setDocument(String document) {
        this.document = document;
    }

    protected void setAccount(Account account) {
        this.account = account;
    }

    protected void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    protected void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

}
