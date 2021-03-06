package br.com.ztech.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ztech.domain.Conta;
import br.com.ztech.domain.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	
	//Optional<Page<Transacao>> findByContaAgenciaAndContaNumeroContaOrderByDataDesc(Integer agencia, Long numeroConta, Pageable pageable);
	
	Optional<Page<Transacao>> findByContaOrderByDataDesc(Conta conta, Pageable pageable);

}