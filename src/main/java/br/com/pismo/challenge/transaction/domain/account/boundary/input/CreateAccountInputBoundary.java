package br.com.pismo.challenge.transaction.domain.account.boundary.input;

public class CreateAccountInputBoundary {
    private String fullName;
    private String documentCpf;

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
}
