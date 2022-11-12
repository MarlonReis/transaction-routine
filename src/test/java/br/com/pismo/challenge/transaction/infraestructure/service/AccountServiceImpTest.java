package br.com.pismo.challenge.transaction.infraestructure.service;

import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.account.entity.AccountBuilder;
import br.com.pismo.challenge.transaction.domain.account.service.GenerateCodeService;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.customer.entity.CustomerBuilder;
import br.com.pismo.challenge.transaction.domain.exception.AccountException;
import br.com.pismo.challenge.transaction.domain.exception.InvalidDocumentException;
import br.com.pismo.challenge.transaction.domain.shared.IdEntity;
import br.com.pismo.challenge.transaction.repository.AccountRepository;
import br.com.pismo.challenge.transaction.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static br.com.pismo.challenge.transaction.domain.exception.TypException.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImpTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GenerateCodeService generateCodeService;


    @InjectMocks
    private AccountServiceImp accountService;

    private Customer customer;
    private Account account;

    @BeforeEach
    void setUp() {
        customer = CustomerBuilder.builder().withCreateAt(new Date()).
                withId(UUID.randomUUID()).withDocument("73823594095").
                withFullName("Fulano de Tals").build();

        account = AccountBuilder.builder().
                withAccountNumber("000000").
                withCodeBank("00").withCreateAt(new Date()).
                withCustomer(customer).
                withAgency("0000").withAccountBalance(BigDecimal.ZERO).
                withId(UUID.fromString("91afc1d6-0f1e-4fb0-a3bb-ddc3b35f972c")).
                build();

    }

    @Test
    @DisplayName("should return account data when create with success")
    void shouldReturnAccountDataWhenCreate() {
        when(generateCodeService.generateAccountNumber()).thenReturn("000000");
        when(generateCodeService.getCodeBank()).thenReturn("00");
        when(generateCodeService.getAgencyNumber()).thenReturn("0000");
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(accountRepository.save(Mockito.any())).thenReturn(account);

        var response = accountService.createAccount(
                new CreateAccountInputBoundary("Fulano de Tals", "73823594095"));

        assertThat(response, Matchers.hasProperty("customerId", Matchers.notNullValue()));
        assertThat(response, Matchers.hasProperty("agency", Matchers.is("0000")));
        assertThat(response, Matchers.hasProperty("accountNumber", Matchers.is("000000")));
        assertThat(response, Matchers.hasProperty("codeBank", Matchers.is("00")));

    }


    @Test
    @DisplayName("should throws InvalidDocumentException when receive invalid document")
    void shouldThrowInvalidDocumentExceptionWhenReceiveInvalidDocument() {
        var exception = assertThrows(InvalidDocumentException.class, () -> accountService.createAccount(
                new CreateAccountInputBoundary("Fulano de Tals", "12365487952")));

        assertThat(exception.getData().getCode(), Matchers.is(DOCUMENT_IS_INVALID));
    }

    @Test
    @DisplayName("should throws AccountException when found customer register with same document")
    void shouldThrowAccountExceptionWhenFoundCustomerRegisterWithSameDocument() {
        Mockito.when(customerRepository.existsByDocument(Mockito.any())).thenReturn(true);

        var exception = assertThrows(AccountException.class, () -> accountService.createAccount(
                new CreateAccountInputBoundary("Fulano de Tals", "38487409075")));

        assertThat(exception.getData().getCode(), Matchers.is(DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT));
    }

    @Test
    @DisplayName("should throw AccountException when has a transaction error")
    void shouldAccountExceptionWhenHasATransactionError() {
        Mockito.when(customerRepository.save(Mockito.any())).thenThrow(new DataIntegrityViolationException("Any message"));

        var exception = assertThrows(AccountException.class, () -> accountService.createAccount(
                new CreateAccountInputBoundary("Fulano de Tals", "38487409075")));

        assertThat(exception.getData().getCode(), Matchers.is(CANNOT_CREATE_ACCOUNT));
    }

    @Test
    @DisplayName("should return account when find by id")
    void shouldReturnAccountWhenFoundById() {
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));

        var response = accountService.getAccountById(new IdEntity("91afc1d6-0f1e-4fb0-a3bb-ddc3b35f972c"));

        assertThat(response.getAccountId(), Matchers.is(account.getId().toString()));
        assertThat(response.getDocumentNumber(), Matchers.is(account.getCustomer().getId().toString()));
    }

    @Test
    @DisplayName("should throws AccountException when account not found")
    void shouldThrowAccountExceptionWhenAccountNotFoundById() {
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var exception = assertThrows(AccountException.class, () -> accountService.
                getAccountById(new IdEntity("91afc1d6-0f1e-4fb0-a3bb-ddc3b35f972c")));

        assertThat(exception.getData().getCode(), Matchers.is(REGISTER_NOT_FOUND));
    }

}