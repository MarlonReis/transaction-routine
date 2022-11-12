package br.com.pismo.challenge.transaction.domain.transaction.boundary.input;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SaveTransactionInputBoundary {
    @NotNull(message = "Attribute 'accountId' is required!")
    private String accountId;

    @NotNull(message = "Attribute 'operationTypeId' is required!")
    private Integer operationTypeId;

    @NotNull(message = "Attribute 'amount' is required!")
    private BigDecimal amount;

    public SaveTransactionInputBoundary(String accountId, Integer operationTypeId, BigDecimal amount) {
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    public SaveTransactionInputBoundary() {
    }

    public String getAccountId() {
        return accountId;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
