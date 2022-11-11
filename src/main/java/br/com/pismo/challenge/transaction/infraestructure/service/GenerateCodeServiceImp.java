package br.com.pismo.challenge.transaction.infraestructure.service;

import br.com.pismo.challenge.transaction.domain.account.service.GenerateCodeService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateCodeServiceImp implements GenerateCodeService {
    @Override
    public String generateAccountNumber() {
        Random rnd = new Random();
        long number = Math.abs(rnd.nextInt());
        return String.format("%07d", number);
    }

    @Override
    public String getAgencyNumber() {
        Random rnd = new Random();
        long number = Math.abs(rnd.nextInt(99));
        return String.format("%04d", number);
    }

    @Override
    public String getCodeBank() {
        return "758";
    }
}
