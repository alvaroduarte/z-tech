package br.com.ztech.service;

import static br.com.ztech.eum.ConfiguracaoPorcentagemEnum.CUSTO_RETIRADA;
import static br.com.ztech.eum.TipoTransacaoEnum.RETIRAR_DINHEIRO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ztech.domain.Conta;
import br.com.ztech.domain.TipoTransacao;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.ContaRepository;
import br.com.ztech.repository.TransacaoRepository;
import br.com.ztech.service.strategy.Movimentacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RetirarService extends ContaService implements Movimentacao {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public Conta movimentacao(Integer agencia, Long numeroConta, BigDecimal valorMovimentacao) {
		
		final var conta = buscarConta(agencia, numeroConta);
		
		return movimentacao( conta  , valorMovimentacao, null );
	
	}
	
	public Conta movimentacao(Long id, BigDecimal valorMovimentacao) {
		
		final var conta = buscarContaPorId(id);
		
		return movimentacao( conta , valorMovimentacao, null );
	}

	@Transactional
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao) {

		log.info("retirar {}, valorMovimentacao {}", conta, valorMovimentacao);
		
		final var porcentagemRetirada = buscarConfiguracaoPorcentagem(CUSTO_RETIRADA.getValor()).getPorcentagem();

		final var valorSaldo = conta.getSaldo();
		
		final var valorPorcentagem = getValorPorcentagem ( porcentagemRetirada );

		final var valorRetirada = calculoValorPorcentagem( valorPorcentagem , valorMovimentacao ); 
		
		final var valorSaldoAtualizado = calculaSaldoRetirada( valorSaldo, valorMovimentacao, valorRetirada );
		
		final var valorTransacao = calculoValorTransacao(valorMovimentacao, valorRetirada);

		log.debug("{}, valorSaldo {}, porcentagemRetirada {}, valorRetirada {}, valorTransacao {}, valorSaldoAtualizado {}", 
				conta, 
				valorSaldo, 
				porcentagemRetirada, 
				valorRetirada, 
				valorTransacao,
				valorSaldoAtualizado);

		validaValorSaldo(valorSaldoAtualizado);

		conta.setSaldo(valorSaldoAtualizado);

		conta = contaRepository.save(conta);
		
		final var transacao = Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(valorSaldo)
				.valorMovimentacao(valorMovimentacao)
				.porcentagemMovimentacao(porcentagemRetirada)
				.valorTransacao(valorTransacao)
				.valorSaldoAtualizado(valorSaldoAtualizado)
				.conta(conta)
				.tipoTransacao(new TipoTransacao(RETIRAR_DINHEIRO.getCodigo()))
				.build();

		transacaoRepository.save(transacao);

		log.debug("retirada efetuado com sucesso na {}", conta);

		return conta;
	}

	protected BigDecimal calculaSaldoRetirada(BigDecimal valorSaldo, BigDecimal valorMovimentacao, BigDecimal valorRetirada) {

		log.info("calculaSaldoRetirada valorSaldo {}, valorMovimentacao {},  valorRetirada {}", valorSaldo, valorMovimentacao, valorRetirada);

		final var saldoAtualizado = valorSaldo.subtract( valorMovimentacao ).subtract( valorRetirada ).setScale(2, RoundingMode.HALF_UP);;

		log.debug("saldoAtualizado {}", saldoAtualizado);

		return saldoAtualizado;
	}
	
	protected BigDecimal getValorPorcentagem(BigDecimal porcentagemRetirada) {
		
		log.info("getValorPorcentagem porcentagemRetirada {}", porcentagemRetirada);
		
		if(porcentagemRetirada.signum() < 0) {
			
			var porcentagemRetiradaRetorno =  porcentagemRetirada.multiply(new BigDecimal(-1));
			
			log.debug("porcentagemRetiradaRetorno {}", porcentagemRetiradaRetorno);
			
			return porcentagemRetiradaRetorno;
		}
		
		return porcentagemRetirada;
	}
	
}