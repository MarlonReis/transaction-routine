package br.com.pismo.challenge.transaction.domain.account.boundary.output;

public final class CreateAccountOutputBoundaryBuilder {
    private CreateAccountOutputBoundary boundary;

    private CreateAccountOutputBoundaryBuilder() {
        boundary = new CreateAccountOutputBoundary();
    }

    public static CreateAccountOutputBoundaryBuilder builder() {
        return new CreateAccountOutputBoundaryBuilder();
    }

    public CreateAccountOutputBoundaryBuilder withCustomerId(String customerId) {
        boundary.setCustomerId(customerId);
        return this;
    }

    public CreateAccountOutputBoundaryBuilder withAgency(String agency) {
        boundary.setAgency(agency);
        return this;
    }

    public CreateAccountOutputBoundaryBuilder withAccountNumber(String accountNumber) {
        boundary.setAccountNumber(accountNumber);
        return this;
    }

    public CreateAccountOutputBoundaryBuilder withCodeBank(String codeBank) {
        boundary.setCodeBank(codeBank);
        return this;
    }

    public CreateAccountOutputBoundaryBuilder withNameBank(String nameBank) {
        boundary.setNameBank(nameBank);
        return this;
    }

    public CreateAccountOutputBoundaryBuilder withCustomerName(String name) {
        boundary.setCustomerName(name);
        return this;
    }



    public CreateAccountOutputBoundary build() {
        return boundary;
    }
}
