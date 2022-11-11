package br.com.pismo.challenge.transaction.repository;

import br.com.pismo.challenge.transaction.domain.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Boolean existsByDocument(String document);
}
