package br.com.ztech.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.ztech.domain.Conta;


public interface Movimentacao {
	
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao);
	
	public default BigDecimal calculoValorTransacao(BigDecimal valorMovimentacao, BigDecimal valor) {

		final var valorTransacao = valorMovimentacao.add(valor);

		return valorTransacao;

	}
	
	public default  BigDecimal calculoPorcentagem(BigDecimal valorPorcentagem, BigDecimal valor) {
		
		if(valorPorcentagem.signum() > 0) {
		
			final var valorCalculado = valor.multiply(valorPorcentagem).divide(new BigDecimal(100));

			return valorCalculado;
		}

		return BigDecimal.ZERO;
	}
	
	public default BigDecimal calculaSoma(BigDecimal valorSaldo, BigDecimal valorMovimentacao, BigDecimal valorBonus) {

		final var saldoAtualizado = valorSaldo.add(valorMovimentacao).add(valorBonus).setScale(2, RoundingMode.HALF_UP);

		return saldoAtualizado;
	}
	
	public default BigDecimal calculaRetirada(BigDecimal valorSaldo, BigDecimal valorMovimentacao, BigDecimal valorCusto) {

		final var saldoAtualizado = valorSaldo.subtract( valorMovimentacao ).subtract( valorCusto ).setScale(2, RoundingMode.HALF_UP);;

		return saldoAtualizado;
	}

}
