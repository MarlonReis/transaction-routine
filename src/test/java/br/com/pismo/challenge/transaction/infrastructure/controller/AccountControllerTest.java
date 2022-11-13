package br.com.pismo.challenge.transaction.infrastructure.controller;

import br.com.pismo.challenge.transaction.ChallengeApplication;
import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.entity.AccountBuilder;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.customer.entity.CustomerBuilder;
import br.com.pismo.challenge.transaction.domain.customer.value.object.DocumentCPF;
import br.com.pismo.challenge.transaction.domain.exception.TypException;
import br.com.pismo.challenge.transaction.repository.AccountRepository;
import br.com.pismo.challenge.transaction.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private CustomerRepository customerRepository;

    @SpyBean
    private AccountRepository accountRepository;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should return 200 when create account with success")
    void shouldReturn200WhenAccountCreateWithSuccess() throws Exception {
        final var body = new CreateAccountInputBoundary("Bertana de Tals", "41106307038");

        mockMvc.perform(post("/v1/accounts").contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return 422 when document is invalid")
    void shouldReturn422WhenDocumentIsInvalid() throws Exception {
        final var body = new CreateAccountInputBoundary("Bertana de Tals", "12345678901");
        mockMvc.perform(post("/v1/accounts").contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).content(mapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value(TypException.DOCUMENT_IS_INVALID.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));

    }

    @Test
    @DisplayName("should return 422 when document is being used by another account")
    void shouldReturn422WhenDocumentIsBeingUsedByAnotherAccount() throws Exception {
        customerRepository.save(new Customer(new DocumentCPF("41106307038"), "Other Name"));

        final var body = new CreateAccountInputBoundary("Bertana de Tals", "41106307038");
        mockMvc.perform(post("/v1/accounts").contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).content(mapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value(TypException.DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));

    }

    @Test
    @DisplayName("should return 500 when cannot create account")
    void shouldReturn500WhenCannotCreateAccount() throws Exception {
        BDDMockito.willThrow(DataIntegrityViolationException.class).
                given(accountRepository).save(BDDMockito.any());

        final var body = new CreateAccountInputBoundary("Bertana de Tals", "41106307038");
        mockMvc.perform(post("/v1/accounts").contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).content(mapper.writeValueAsString(body)))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.code").value(TypException.INTERNAL_ERROR_SERVER.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));
    }

    @Test
    @DisplayName("should return 500 when internal class throw exception")
    void shouldReturn500WhenInternalClassThrowAnyRuntimeException() throws Exception {
        BDDMockito.willThrow(new NullPointerException("Message")).given(customerRepository).existsByDocument("41106307038");

        final var body = new CreateAccountInputBoundary("Bertana de Tals", "41106307038");
        mockMvc.perform(post("/v1/accounts").contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).content(mapper.writeValueAsString(body)))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.code").value(TypException.INTERNAL_ERROR_SERVER.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));
    }

    @Test
    @DisplayName("should return 400 when request attribute is null")
    void shouldReturn400WhenRequestAttributeIsNull() throws Exception {
        final var body = new CreateAccountInputBoundary("Bertana de Tals", null);
        mockMvc.perform(post("/v1/accounts").contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).content(mapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(TypException.INVALID_REQUEST.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));
    }

    @Test
    @DisplayName("should return 400 when request doesn't has body")
    void shouldReturn400WhenRequestWithoutBody() throws Exception {
        mockMvc.perform(post("/v1/accounts").contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(TypException.INVALID_REQUEST.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));
    }

    @Test
    @DisplayName("should return 404 when account not found by id")
    void shouldReturn404WhenNotFoundAccountById() throws Exception {
        mockMvc.perform(get("/v1/accounts/{accountId}", "96579a4e-a4e3-470a-bd7e-65e478de138e")
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(TypException.REGISTER_NOT_FOUND.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));
    }

    @Test
    @DisplayName("should return 422 when identity is invalid")
    void shouldReturn422WhenNotFoundAccountById() throws Exception {
        mockMvc.perform(get("/v1/accounts/{accountId}", "96579a4e-a4e3-470a-bd7e")
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value(TypException.IDENTITY_VALUE_IS_INVALID.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));
    }

    @Test
    @DisplayName("should return 200 when found account register")
    void shouldReturn200WhenFoundAccount() throws Exception {
        var account = AccountBuilder.builder().withId(UUID.randomUUID()).
                withCustomer(CustomerBuilder.builder().withDocument("76612497076").
                        build()).build();

        BDDMockito.willReturn(Optional.of(account)).given(accountRepository).findById(BDDMockito.any());

        mockMvc.perform(get("/v1/accounts/{accountId}", "eea5d2b5-0422-4334-b8fe-3bb92270c696")
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.documentNumber").value(Matchers.notNullValue()));
    }


}