package br.com.pismo.challenge.transaction.infraestructure.service;

import br.com.pismo.challenge.transaction.domain.account.boundary.input.CreateAccountInputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.CreateAccountOutputBoundary;
import br.com.pismo.challenge.transaction.domain.account.boundary.output.CreateAccountOutputBoundaryBuilder;
import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import br.com.pismo.challenge.transaction.domain.account.service.AccountService;
import br.com.pismo.challenge.transaction.domain.account.service.GenerateCodeService;
import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import br.com.pismo.challenge.transaction.domain.customer.value.object.DocumentCPF;
import br.com.pismo.challenge.transaction.domain.exception.AccountException;
import br.com.pismo.challenge.transaction.repository.AccountRepository;
import br.com.pismo.challenge.transaction.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImp implements AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountServiceImp.class);

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final GenerateCodeService generateCodeService;

    @Autowired
    public AccountServiceImp(AccountRepository account, CustomerRepository customer, GenerateCodeService generateCodeService) {
        this.accountRepository = account;
        this.customerRepository = customer;
        this.generateCodeService = generateCodeService;
    }

    @Override
    @Transactional()
    public CreateAccountOutputBoundary createAccount(CreateAccountInputBoundary boundary) {
        final var document = new DocumentCPF(boundary.getDocumentCpf());
        try {
            if (customerRepository.existsByDocument(document.value())) {
                logger.info("Customer cannot create account because the document is being used: document: {}", document.value());
                throw AccountException.documentIsBeUsedByAnotherAccount("This document is being used by another customer and another account!");
            }
            var customer = customerRepository.save(new Customer(document, boundary.getFullName()));
            var account = accountRepository.save(new Account(
                    generateCodeService.getAgencyNumber(),
                    generateCodeService.generateAccountNumber(),
                    generateCodeService.getCodeBank(),
                    BigDecimal.ZERO, customer));

            return CreateAccountOutputBoundaryBuilder.builder().
                    withCustomerName(customer.getFullName()).
                    withAccountNumber(account.getAccountNumber()).
                    withAgency(account.getAgency()).withCodeBank(account.getCodeBank()).
                    withCustomerId(customer.getId().toString()).withNameBank("Bank XPTO").
                    build();

        } catch (DataIntegrityViolationException ex) {
            logger.error("Error when trying create an account: exception: {}, message:{}", ex.getCause(), ex.getMessage());
            throw AccountException.cannotCreateAccount("Unable to create an account at this time. Try later!");
        }
    }
}
