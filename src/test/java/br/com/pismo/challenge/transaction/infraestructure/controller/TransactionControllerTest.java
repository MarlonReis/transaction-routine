package br.com.pismo.challenge.transaction.infraestructure.controller;

import br.com.pismo.challenge.transaction.ChallengeApplication;
import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.GetAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.account.entity.AccountBuilder;
import br.com.pismo.challenge.transaction.domain.account.service.AccountService;
import br.com.pismo.challenge.transaction.domain.exception.TypException;
import br.com.pismo.challenge.transaction.domain.shared.IdEntity;
import br.com.pismo.challenge.transaction.domain.transaction.boundary.input.SaveTransactionInputBoundary;
import br.com.pismo.challenge.transaction.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private TransactionRepository transactionRepository;

    @SpyBean
    private AccountService accountService;

    private ObjectMapper mapper;

    private MockHttpServletRequestBuilder request;

    private String accountId = "9d0dc4c6-b9a3-439a-808f-28d13258645a";

    private GetAccountOutputBoundary account;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        request = post("/v1/transactions").contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        account = new GetAccountOutputBoundary(accountId, "34144341054");
    }

    @Test
    @DisplayName("should return 200 when save transaction with success")
    void shouldReturn200WhenSaveTransactionWithSuccess() throws Exception {
        BDDMockito.willReturn(account).given(accountService).getAccountById(BDDMockito.any());

        final var body = new SaveTransactionInputBoundary(accountId, 1, BigDecimal.valueOf(10.0));
        mockMvc.perform(request.content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return 404 when account not found")
    void shouldReturn404WhenAccountNotFound() throws Exception {
        final var body = new SaveTransactionInputBoundary(accountId, 1, BigDecimal.valueOf(10.0));
        mockMvc.perform(request.content(mapper.writeValueAsString(body)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(TypException.REGISTER_NOT_FOUND.toString()))
                .andExpect(jsonPath("$.message").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.date").value(Matchers.notNullValue()));
    }

    @Test
    @DisplayName("should return 422 when operation type is unknown")
    void shouldReturn422WhenOperationTypeIsUnknown() throws Exception {
        final var body = new SaveTransactionInputBoundary(accountId, 8, BigDecimal.valueOf(10.0));
        mockMvc.perform(request.content(mapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").
                        value(TypException.UNKNOWN_TRANSACTION.toString()));
    }

    @Test
    @DisplayName("should return 422 when accountId is invalid")
    void shouldReturn422WhenAccountIsInvalid() throws Exception {
        var invalidId = "9d0dc4c6-b9a3-439a-808f";
        final var body = new SaveTransactionInputBoundary(invalidId, 1, BigDecimal.valueOf(10.0));
        mockMvc.perform(request.content(mapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").
                        value(TypException.IDENTITY_VALUE_IS_INVALID.toString()));
    }

    @Test
    @DisplayName("should return 422 when purchase receive negative value")
    void shouldReturn422WhenReceiveAmountAndOperationAreInvalid() throws Exception {
        BDDMockito.willReturn(account).given(accountService).getAccountById(BDDMockito.any());

        final var body = new SaveTransactionInputBoundary(accountId, 1, BigDecimal.valueOf(-10.0));
        mockMvc.perform(request.content(mapper.writeValueAsString(body)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").
                        value(TypException.PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA.toString()));
    }


}