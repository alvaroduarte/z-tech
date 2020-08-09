package br.com.ztech.service.strategy;

import java.math.BigDecimal;

import br.com.ztech.domain.Conta;

public interface Movimentacao {
	
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao);

}
