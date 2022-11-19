package br.com.pismo.challenge.transaction.domain.account.value.object;

import br.com.pismo.challenge.transaction.domain.exception.LimitException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.CREDIT_LIMIT_CANNOT_BE_NEGATIVE;
import static br.com.pismo.challenge.transaction.domain.exception.TypException.CREDIT_LIMIT_IS_REQUIRED;
import static org.junit.jupiter.api.Assertions.*;

class CreditLimitTest {

    @Test
    @DisplayName("should return a valid limit")
    void shouldReturnValidLimit() {
        var value = new CreditLimit(new BigDecimal(10));
        assertEquals(new BigDecimal(10), value.getValue());
    }

    @Test
    @DisplayName("should throw LimitException when limit is negative")
    void shouldThrowInvalidLimitExceptionWhenLimitIsNegative() {
        var exception = assertThrows(LimitException.class, () -> new CreditLimit(new BigDecimal(-10)));
        assertEquals(CREDIT_LIMIT_CANNOT_BE_NEGATIVE, exception.getData().getCode());
    }

    @Test
    @DisplayName("should throw LimitException when value is null")
    void shouldThrowInvalidLimitExceptionWhenValueIsNull() {
        var exception = assertThrows(LimitException.class, () -> new CreditLimit(null));
        assertEquals(CREDIT_LIMIT_IS_REQUIRED, exception.getData().getCode());
    }

}