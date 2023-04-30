package one.digitalinnovation.lppsp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.lppsp.exceptions.ResourceNotFoundException;
import one.digitalinnovation.lppsp.feignclient.ViaCepFeign;
import one.digitalinnovation.lppsp.model.Cliente;
import one.digitalinnovation.lppsp.model.Endereco;
import one.digitalinnovation.lppsp.repository.ClienteRepository;
import one.digitalinnovation.lppsp.repository.EnderecoRepository;
import one.digitalinnovation.lppsp.service.ClienteService;
import one.digitalinnovation.lppsp.util.Constants;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ViaCepFeign viaCepFeign;

	@Override
	public Iterable<Cliente> buscarTodos(){
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.CLIENT_NOT_FOUND + id));
	}

	@Override
	public void inserir(Cliente cliente) {
		if(cliente.getEndereco() != null) {
			salvarClienteComCep(cliente);			
		} else {
			salvarCliente(cliente);
		}
	}

	@Override
	public void atualizar(Long id, Cliente newCliente) {
		Cliente cliente = buscarPorId(id);
		newCliente.setId(cliente.getId());
		if(newCliente.getNome() == null) {newCliente.setNome(cliente.getNome());}
		if(newCliente.getEndereco() != null) {
			salvarClienteComCep(newCliente);			
		} else {
			if(cliente.getEndereco() != null) {
				newCliente.setEndereco(cliente.getEndereco());
			}
			salvarCliente(newCliente);
		}
	}

	@Override
	public void deletar(Long id) {
		clienteRepository.delete(buscarPorId(id));
	}
	
	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco newEndereco = viaCepFeign.consultarCep(cep);
			enderecoRepository.save(newEndereco);
			return newEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}
	
	private void salvarCliente(Cliente cliente) {
		clienteRepository.save(cliente);
	}
}
