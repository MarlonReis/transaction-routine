package br.com.pismo.challenge.transaction.domain.account.boundary.input;


import javax.validation.constraints.NotNull;

public class CreateAccountInputBoundary {
    @NotNull(message = "Attribute 'fullName' is required!")
    private String fullName;
    @NotNull(message = "Attribute 'documentCpf' is required!")
    private String documentCpf;

    public CreateAccountInputBoundary() {
    }

    public CreateAccountInputBoundary(String fullName, String documentCpf) {
        this.fullName = fullName;
        this.documentCpf = documentCpf;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocumentCpf() {
        return documentCpf;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDocumentCpf(String documentCpf) {
        this.documentCpf = documentCpf;
    }
}
