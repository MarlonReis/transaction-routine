package br.com.pismo.challenge.transaction.domain.transaction.entity;

import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.exception.*;
import br.com.pismo.challenge.transaction.domain.transaction.constant.TransactionType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -10.0, -30.00, -0.3, -10000.000})
    @DisplayName("should throw PurchaseTransactionException when purchaseTransaction received negative value")
    void shouldThrowPurchaseTransactionExceptionWhenPurchaseTransactionReceiveNegativeValue(double value) {
        DomainException exception = assertThrows(PurchaseTransactionException.class, () ->
                transaction.purchaseTransaction(BigDecimal.valueOf(value), TransactionType.CASH_PURCHASE));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA));
        assertThat(exception.getData().getMessage(), Matchers.is("Purchase transaction value receive negative value!"));
    }

    @Test
    @DisplayName("should throw PurchaseTransactionException when purchaseTransaction received null value")
    void shouldThrowPurchaseTransactionExceptionWhenPurchaseTransactionReceiveNullValue() {
        DomainException exception = assertThrows(PurchaseTransactionException.class, () ->
                transaction.purchaseTransaction(null, TransactionType.CASH_PURCHASE));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA));
        assertThat(exception.getData().getMessage(), Matchers.is("Purchase transaction value is required!"));
    }

    @Test
    @DisplayName("should throw PurchaseTransactionException when purchaseTransaction receive zero")
    void shouldThrowPurchaseTransactionExceptionWhenPurchaseTransactionReceiveZeroValue() {
        DomainException exception = assertThrows(PurchaseTransactionException.class, () ->
                transaction.purchaseTransaction(BigDecimal.ZERO, TransactionType.INSTALLMENT_PURCHASE));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA));
        assertThat(exception.getData().getMessage(), Matchers.is("Purchase transaction value receive invalid value!"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 10.0, 30.00, 0.3, 10000.000})
    @DisplayName("should contains negative value when purchase transaction")
    void shouldContainsNegativeValueWhenPurchaseTransaction(double value) {
        transaction.purchaseTransaction(BigDecimal.valueOf(value), TransactionType.CASH_PURCHASE);
        assertThat(transaction.getValue().signum(), Matchers.is(-1));
    }

    @Test
    @DisplayName("should throws PurchaseTransactionException when receive transaction type invalid")
    void shouldThrowPurchaseTransactionExceptionWhenReceiveTractionTypeInvalid() {
        DomainException exception = assertThrows(PurchaseTransactionException.class, () ->
                transaction.purchaseTransaction(BigDecimal.TEN, TransactionType.PAYMENT));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA));
        assertThat(exception.getData().getMessage(), Matchers.is("Purchase transaction type 'PAYMENT' is invalid!"));
    }

    @Test
    @DisplayName("should throws PurchaseTransactionException when receive transaction type null")
    void shouldThrowPurchaseTransactionExceptionWhenReceiveTractionTypeNull() {
        DomainException exception = assertThrows(PurchaseTransactionException.class, () ->
                transaction.purchaseTransaction(BigDecimal.TEN, null));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA));
        assertThat(exception.getData().getMessage(), Matchers.is("Purchase transaction type is required!"));
    }


    @Test
    @DisplayName("should throws ThrowsWithdrawMoneyException when withdrawMoney receive null")
    void shouldThrowsWithdrawMoneyExceptionWhenWithdrawMoneyReceiveValueNull() {
        DomainException exception = assertThrows(WithdrawMoneyException.class, () -> transaction.withdrawMoney(null));
        assertThat(exception.getData().getCode(), Matchers.is(TypException.WITHDRAW_MONEY_RECEIVED_INVALID_VALUE));
        assertThat(exception.getData().getMessage(), Matchers.is("Withdraw money value is required!"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -2.0, -4.0, -3.0, 0.0})
    @DisplayName("should throws ThrowsWithdrawMoneyException when withdrawMoney receive value less than zero")
    void shouldThrowsWithdrawMoneyExceptionWhenWithdrawMoneyReceiveValueLessThanZero(double value) {
        DomainException exception = assertThrows(WithdrawMoneyException.class,
                () -> transaction.withdrawMoney(BigDecimal.valueOf(value)));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.WITHDRAW_MONEY_RECEIVED_INVALID_VALUE));
        assertThat(exception.getData().getMessage(), Matchers.is("Withdraw money value must be greater than zero!"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.1, 12.2, 4.24, 3.10, 0.10})
    @DisplayName("should throws ThrowsWithdrawMoneyException when withdrawMoney receive fractional value")
    void shouldThrowsWithdrawMoneyExceptionWhenReceiveFractionalValue(double value) {
        DomainException exception = assertThrows(WithdrawMoneyException.class,
                () -> transaction.withdrawMoney(BigDecimal.valueOf(value)));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.WITHDRAW_MONEY_RECEIVED_INVALID_VALUE));
        assertThat(exception.getData().getMessage(), Matchers.is("The withdrawal amount must be a whole number!"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 10.0, 30.00, 3, 40000.000})
    @DisplayName("should contains negative value when withdraw money")
    void shouldContainsNegativeValue(double value) {
        transaction.withdrawMoney(BigDecimal.valueOf(value));
        assertThat(transaction.getValue().signum(), Matchers.is(-1));
    }

    @Test
    @DisplayName("should throws PaymentException when payment receive null value")
    void shouldThrowPaymentExceptionWhenPaymentReceiveNullValue() {
        DomainException exception = assertThrows(PaymentException.class,
                () -> transaction.payment(null));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.PAYMENT_RECEIVED_INVALID_VALUE));
        assertThat(exception.getData().getMessage(), Matchers.is("payment value is required!"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -2.0, -4.0, -3.0, 0.0})
    @DisplayName("should throws PaymentException when payment receive value less than zero")
    void shouldThrowPaymentExceptionWhenPaymentReceiveValueLessThanZero(double value) {
        DomainException exception = assertThrows(PaymentException.class,
                () -> transaction.payment(BigDecimal.valueOf(value)));

        assertThat(exception.getData().getCode(), Matchers.is(TypException.PAYMENT_RECEIVED_INVALID_VALUE));
        assertThat(exception.getData().getMessage(), Matchers.is("payment value must be greater than zero!"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 4.0, 3.0, 0.1})
    @DisplayName("should contains the same value when do a payment")
    void shouldContainsTheSameValueWhenPayment(double value) {
        transaction.payment(BigDecimal.valueOf(value));
        assertThat(transaction.getValue(), Matchers.is(BigDecimal.valueOf(value)));
    }


}