package one.digitalinnovation.lppsp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import one.digitalinnovation.lppsp.model.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, String>{

}
