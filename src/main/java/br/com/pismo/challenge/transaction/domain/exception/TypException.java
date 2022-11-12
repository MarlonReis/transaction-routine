package br.com.pismo.challenge.transaction.domain.exception;

public enum TypException {
    DOCUMENT_IS_INVALID,
    PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA,
    WITHDRAW_MONEY_RECEIVED_INVALID_VALUE,
    PAYMENT_RECEIVED_INVALID_VALUE,
    CANNOT_CREATE_ACCOUNT,
    INTERNAL_ERROR_SERVER,
    INVALID_REQUEST,
    IDENTITY_VALUE_IS_INVALID,
    REGISTER_NOT_FOUND,
    DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT;


    @Override
    public String toString() {
        return this.name();
    }
}
