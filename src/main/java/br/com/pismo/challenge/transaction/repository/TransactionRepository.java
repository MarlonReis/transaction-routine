package br.com.pismo.challenge.transaction.repository;

import br.com.pismo.challenge.transaction.domain.transaction.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
}
