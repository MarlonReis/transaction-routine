package br.com.pismo.challenge.transaction.domain.shared.value.object;

import br.com.pismo.challenge.transaction.domain.exception.DomainException;
import br.com.pismo.challenge.transaction.domain.exception.IdentityException;
import br.com.pismo.challenge.transaction.domain.exception.TypException;
import br.com.pismo.challenge.transaction.domain.shared.value.object.IdEntity;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class IdEntityTest {

    @Test
    @DisplayName("should return valid id when receive valid value")
    void shouldReturnValidIdWhenReceivedValidValue() {
        String stringId = "b115970b-c808-45be-961a-71f15c4e98d4";
        final var id = new IdEntity(stringId);

        assertThat(id.value(), CoreMatchers.is(UUID.fromString(stringId)));
        assertThat(id.value().toString(), CoreMatchers.is(stringId));
    }

    @Test
    @DisplayName("should throws error when receive null")
    void shouldThrowsIdentityExceptionWhenReceiveNullValue() {
        DomainException exception = assertThrows(IdentityException.class, () -> new IdEntity(null));

        assertThat(exception.getData().getMessage(), is("Identity is required!"));
        assertThat(exception.getData().getCode(), is(TypException.IDENTITY_VALUE_IS_INVALID));
        assertThat(exception.getData().getDate(), CoreMatchers.anything());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "b115970b-c808-45be-961a", "id", ""})
    @DisplayName("should throws error when receive identity invalid")
    void shouldThrowsIdentityExceptionWhenReceiveIdentityInvalid(String id) {
        DomainException exception = assertThrows(IdentityException.class, () -> new IdEntity(id));

        assertThat(exception.getData().getMessage(), is("Identity is invalid!"));
        assertThat(exception.getData().getCode(), is(TypException.IDENTITY_VALUE_IS_INVALID));
        assertThat(exception.getData().getDate(), CoreMatchers.anything());
    }


}