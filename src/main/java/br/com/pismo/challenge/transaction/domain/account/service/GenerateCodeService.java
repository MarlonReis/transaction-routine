package br.com.pismo.challenge.transaction.domain.account.service;

public interface GenerateCodeService {
    String generateAccountNumber();

    String getAgencyNumber();

    String getCodeBank();
}

