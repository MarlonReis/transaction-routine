package br.com.pismo.challenge.transaction.repository;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.customer.value.object.DocumentCPF;
import org.exparity.hamcrest.date.DateMatchers;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Date;


import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-test.properties")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;


    @Test
    @DisplayName("should salve correct account data")
    void shouldCreateCorrectAccount() {
        Customer customer = new Customer(new DocumentCPF("759.048.250-13"), "Fulano de Tals");
        Account account = new Account("232", "423423", "1", BigDecimal.TEN, customer);
        Account response = repository.save(account);

        assertThat(response.getId(), CoreMatchers.notNullValue());
        assertThat(response.getCreateAt(), DateMatchers.sameSecondOfMinute(new Date()));
        assertThat(response.getAccountBalance(), CoreMatchers.is(new BigDecimal(10)));
        assertThat(response.getCodeBank(), CoreMatchers.is("1"));
        assertThat(response.getCustomer(), CoreMatchers.notNullValue());
    }

}