package br.com.pismo.challenge.transaction.domain.account.boundary.output;

public class GetAccountOutputBoundary {
    private String accountId;
    private String documentNumber;


    public GetAccountOutputBoundary() {
    }

    public GetAccountOutputBoundary(String accountId, String documentNumber) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }
}
