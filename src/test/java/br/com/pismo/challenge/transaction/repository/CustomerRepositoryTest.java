package br.com.pismo.challenge.transaction.repository;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.customer.value.object.DocumentCPF;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-test.properties")
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(new DocumentCPF("759.048.250-13"), "Fulano de Tals");
    }



    @Test
    @DisplayName("should return true when found customer data with the same document")
    void shouldReturnTrueWhenFoundCustomerRegisterByDocument() {
        repository.save(customer);
        var customerExists = repository.existsByDocument(customer.getDocument());
        assertTrue(customerExists);
    }

    @Test
    @DisplayName("should throw exception when customer document is duplicate")
    void shouldThrowsExceptionWhenCustomerDocumentIsDuplicate() {
        final var exception = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(new Customer(new DocumentCPF("759.048.250-13"), "Fulano de Tals"));
            repository.save(new Customer(new DocumentCPF("759.048.250-13"), "Fulano de Tals"));
            repository.findAll();
        });

        assertThat(exception.toString(), CoreMatchers.containsString("UniqueCustomerDocument"));
    }
}