package br.com.ztech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ztech.domain.TipoTransacao;

public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {
	
}