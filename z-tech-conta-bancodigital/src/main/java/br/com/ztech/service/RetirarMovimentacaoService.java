package br.com.ztech.service;

import static br.com.ztech.eum.TipoTransacaoEnum.RETIRAR_DINHEIRO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ztech.domain.Conta;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.TransacaoRepository;
import br.com.ztech.service.strategy.Movimentacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RetirarMovimentacaoService implements Movimentacao {

	@Autowired
	private ContaService contaService;
	
	@Autowired
	private TipoTransacaoService tipoTransacaoService;

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public Conta movimentacao(Integer agencia, Long numeroConta, BigDecimal valorMovimentacao) {
		
		final var conta = contaService.buscarConta(agencia, numeroConta);
		
		return movimentacao( conta  , valorMovimentacao, null );
	
	}
	
	public Conta movimentacao(Long id, BigDecimal valorMovimentacao) {
		
		final var conta = contaService.buscarContaPorId(id);
		
		return movimentacao( conta , valorMovimentacao, null );
	}

	@Transactional
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao) {

		log.info("retirar {}, valorMovimentacao {}", conta, valorMovimentacao);
		
		final var tipoTransacao = tipoTransacaoService.burcarPorId(RETIRAR_DINHEIRO.getCodigo());
		
		final var porcentagemRetirada = tipoTransacao.getPorcentagem();

		final var valorSaldo = conta.getSaldo();
		
		final var valorPorcentagem = getValorPorcentagem ( porcentagemRetirada );

		final var valorRetirada = calculoPorcentagem( valorPorcentagem , valorMovimentacao ); 
		
		final var valorSaldoAtualizado = calculaRetirada( valorSaldo, valorMovimentacao, valorRetirada );
		
		final var valorTransacao = calculoValorTransacao(valorMovimentacao, valorRetirada);

		log.debug("{}, valorSaldo {}, porcentagemRetirada {}, valorRetirada {}, valorTransacao {}, valorSaldoAtualizado {}", 
				conta, 
				valorSaldo, 
				porcentagemRetirada, 
				valorRetirada, 
				valorTransacao,
				valorSaldoAtualizado);

		contaService.validaValorSaldoConta(valorSaldoAtualizado);

		conta.setSaldo(valorSaldoAtualizado);

		conta = contaService.salvar(conta);
		
		final var transacao = Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(valorSaldo)
				.valorMovimentacao(valorMovimentacao)
				.porcentagemMovimentacao(porcentagemRetirada)
				.valorTransacao(valorTransacao)
				.valorSaldoAtualizado(valorSaldoAtualizado)
				.conta(conta)
				.tipoTransacao(tipoTransacao)
				.build();

		transacaoRepository.save(transacao);

		log.debug("retirada efetuado com sucesso na {}", conta);

		return conta;
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