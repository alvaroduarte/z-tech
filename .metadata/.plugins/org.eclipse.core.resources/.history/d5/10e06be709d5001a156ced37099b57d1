package br.com.ztech.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ztech.controller.ContaController;
import br.com.ztech.domain.Transacao;
import br.com.ztech.exception.ExtratoNotFoundException;
import br.com.ztech.repository.TransacaoRepository;

@Service
public class TransacaoService {
	
	private final static Logger logger = LogManager.getLogger(ContaController.class);
	
	@Autowired
	private TransacaoRepository transacaoRepository;

	public List<Transacao> buscarTransacoes(Integer agencia, Integer numeroConta) {
		
		logger.info("buscarTransacoes {}, {}", numeroConta, agencia);
		
		return transacaoRepository.findByContaAgenciaAndContaConta(agencia, numeroConta).orElseThrow(() -> new ExtratoNotFoundException());
		
	}	
	
}