package br.com.ztech.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.ztech.domain.Cliente;
import br.com.ztech.exception.ClienteJaCadastradoException;
import br.com.ztech.exception.CpfInvalidoException;
import br.com.ztech.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void validaCadastroCliente(String cpf) {
		
		log.info("verificaExisteCadastroCliente {}", cpf);
		
		clienteRepository.findByCpf(cpf)
			.ifPresent(c -> { throw new ClienteJaCadastradoException(); });
		
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		
		log.info("salvar {}", cliente);
		
		var cpf = cliente.getCpf();
		
		validaCpf(cpf);
		
		validaCadastroCliente(cpf);
		
		cliente = clienteRepository.save(cliente);
		
		log.debug("Cliente Salvo com sucesso! {}", cliente);
		
		return cliente;
		
	}
	
	public void validaCpf(String cpf) { 

		log.info("validaCpf {}", cpf);

		final var cpfValidator = new CPFValidator(); 

		final var erros = cpfValidator.invalidMessagesFor(cpf); 
		
		if(erros.stream().findFirst().isPresent()) {

			log.error("Cpf {} inválido", cpf);

			throw new CpfInvalidoException();
		}

		log.debug("Cpf {} valido", cpf);
	}
	
}