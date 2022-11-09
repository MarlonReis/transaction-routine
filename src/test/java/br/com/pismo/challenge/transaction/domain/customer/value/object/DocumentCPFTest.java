package br.com.pismo.challenge.transaction.domain.customer.value.object;

import br.com.pismo.challenge.transaction.domain.exception.DomainException;
import br.com.pismo.challenge.transaction.domain.exception.InvalidDocumentException;
import br.com.pismo.challenge.transaction.domain.exception.TypException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class DocumentCPFTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "61536235300", "931.736.860-39", "241.033.910-73", "534.201.470-07",
            "742.312.460-50", "977.127.970-05", "10330088009", "16101433021", "44181125025",
            "54940405001", "81838961038", "79352855000", "933.251.870-05"
    })
    @DisplayName("should return value when received valid document")
    void shouldReturnValueWhenCPFIsValid(String document) {
        DocumentCPF documentCPF = new DocumentCPF(document);
        assertNotNull(documentCPF.value());
        assertThat(documentCPF.value(), Matchers.not(Matchers.emptyString()));
    }

    @EmptySource
    @ParameterizedTest
    @ValueSource(strings = {"11111111111", "00000000011", "23121231212", "12345678", "0902939929392"})
    @DisplayName("should throws InvalidDocumentException when received invalid document")
    void shouldThrowExceptionWhenDocumentIsInvalid(String document) {
        DomainException exception = assertThrows(InvalidDocumentException.class, () -> new DocumentCPF(document));

        assertThat(exception.getData().getMessage(), is("Document invalid!"));
        assertThat(exception.getData().getCode(), is(TypException.DOCUMENT_IS_INVALID));
        assertThat(exception.getData().getDate(), CoreMatchers.anything());
    }


    @Test
    @DisplayName("should throws InvalidDocumentException when received null value")
    void shouldThrowExceptionWhenParamIsNull() {
        DomainException exception = assertThrows(InvalidDocumentException.class, () -> new DocumentCPF(null));
        assertThat(exception.getData().getMessage(), is("Document is required!"));
        assertThat(exception.getData().getCode(), is(TypException.DOCUMENT_IS_INVALID));
        assertThat(exception.getData().getDate(), CoreMatchers.anything());

    }
}