package br.com.pismo.challenge.transaction.repository;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.customer.value.object.DocumentCPF;
import org.exparity.hamcrest.date.DateMatchers;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-test.properties")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(new DocumentCPF("759.048.250-13"), "Fulano de Tals");
    }


    @Test
    @DisplayName("should salve correct account data")
    void shouldCreateCorrectAccount() {
        Account account = new Account("232", "423423", "1", BigDecimal.TEN, customer);
        Account response = repository.save(account);

        assertThat(response.getId(), CoreMatchers.notNullValue());
        assertThat(response.getCreateAt(), DateMatchers.sameMinuteOfHour(new Date()));
        assertThat(response.getAccountBalance(), CoreMatchers.is(new BigDecimal(10)));
        assertThat(response.getCodeBank(), CoreMatchers.is("1"));
        assertThat(response.getCustomer(), CoreMatchers.notNullValue());
    }

    @Test
    @DisplayName("should throw exception when account number duplicate")
    void shouldThrowsExceptionWhenAccountNumberDuplicate() {
        final var exception = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(new Account("232", "4234233", "1", BigDecimal.TEN, customer));
            repository.save(new Account("232", "4234233", "1", BigDecimal.TEN, customer));
            repository.findAll();
        });

        assertThat(exception.toString(), CoreMatchers.containsStringIgnoringCase("UniqueAccountNumber"));
    }


}