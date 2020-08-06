package br.com.ztech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ztech.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	 Optional<Cliente> findByCpf(String cpf);

}