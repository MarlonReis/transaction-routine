package br.com.pismo.challenge.transaction.domain.transaction.constant;

import br.com.pismo.challenge.transaction.domain.exception.DomainException;
import br.com.pismo.challenge.transaction.domain.exception.TransactionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.UNKNOWN_TRANSACTION;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTypeTest {

    @Test
    @DisplayName("should return TransactionType when identifies by code ")
    void shouldReturnTransactionTypeWhenIdentifiesByCode() {
        assertEquals(TransactionType.CASH_PURCHASE, TransactionType.byCode(1));
        assertEquals(TransactionType.INSTALLMENT_PURCHASE, TransactionType.byCode(2));
        assertEquals(TransactionType.WITHDRAWAL_MONEY, TransactionType.byCode(3));
        assertEquals(TransactionType.PAYMENT, TransactionType.byCode(4));
    }

    @Test
    @DisplayName("should throw TransactionException when don't know the code")
    void shouldThrowTransactionExceptionWhenNotKnowTheCode() {
        DomainException exception = assertThrows(TransactionException.class, () -> TransactionType.byCode(5));
        assertEquals(UNKNOWN_TRANSACTION, exception.getData().getCode());
        assertEquals("Transaction is unknown, cannot be processed!", exception.getData().getMessage());
    }

    @Test
    @DisplayName("should return true when transaction is purchase")
    void shouldReturnTrueWhenTransactionTypeIsPurchase() {
        assertTrue(TransactionType.CASH_PURCHASE.isPurchaseTransaction());
        assertTrue(TransactionType.INSTALLMENT_PURCHASE.isPurchaseTransaction());

        assertFalse(TransactionType.WITHDRAWAL_MONEY.isPurchaseTransaction());
        assertFalse(TransactionType.PAYMENT.isPurchaseTransaction());
    }

}