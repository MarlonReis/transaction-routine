package br.com.pismo.challenge.transaction.domain.account.boundary.input;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateAccountInputBoundary {
    @NotNull(message = "Attribute 'fullName' is required!")
    private String fullName;
    @NotNull(message = "Attribute 'documentCpf' is required!")
    private String documentCpf;

    @NotNull(message = "Attribute 'creditLimit' is required!")
    private BigDecimal creditLimit;

    public CreateAccountInputBoundary() {
    }

    public CreateAccountInputBoundary(String fullName, String documentCpf, BigDecimal creditLimit) {
        this.fullName = fullName;
        this.documentCpf = documentCpf;
        this.creditLimit = creditLimit;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocumentCpf() {
        return documentCpf;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }
}
