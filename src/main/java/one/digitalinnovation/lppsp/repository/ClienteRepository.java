package one.digitalinnovation.lppsp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import one.digitalinnovation.lppsp.model.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
