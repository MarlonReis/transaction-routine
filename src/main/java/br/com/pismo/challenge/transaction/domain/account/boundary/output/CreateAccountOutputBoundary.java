package br.com.pismo.challenge.transaction.domain.account.boundary.output;

public class CreateAccountOutputBoundary {
    private String customerId;
    private String agency;
    private String accountNumber;
    private String codeBank;
    private String customerName;

    public String getCustomerId() {
        return customerId;
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

    public String getCustomerName() {
        return customerName;
    }

    protected void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    protected void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
