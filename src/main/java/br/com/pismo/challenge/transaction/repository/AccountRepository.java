package br.com.pismo.challenge.transaction.repository;

import br.com.pismo.challenge.transaction.domain.account.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {
    Account findAccountById(UUID id);
}
