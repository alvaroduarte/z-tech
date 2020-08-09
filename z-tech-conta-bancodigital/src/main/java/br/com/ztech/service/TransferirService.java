package br.com.ztech.service;

import static br.com.ztech.eum.TipoTransacaoEnum.TRANSFERENCIA_ENTRADA_DINHEIRO;
import static br.com.ztech.eum.TipoTransacaoEnum.TRANSFERENCIA_SAIDA_DINHEIRO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;

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
public class TransferirService extends ContaService implements Movimentacao {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public Conta movimentacao(Integer agencia, Long numeroConta, BigDecimal valorMovimentacao, Integer agenciaDestino, Long numeroContaDestino) {
		
		final var conta = buscarConta(agencia, numeroConta);
		
		final var contaDestino = buscarConta(agenciaDestino, numeroContaDestino);
		
		return movimentacao(conta, valorMovimentacao, contaDestino);
	
	}
	
	public Conta movimentacao(Long id, BigDecimal valorMovimentacao, Integer agenciaDestino, Long numeroContaDestino) {
	
		final var conta = buscarContaPorId(id);
		
		final var contaDestino = buscarConta(agenciaDestino, numeroContaDestino);
		
		return movimentacao(conta, valorMovimentacao, contaDestino);
	
	}

	@Transactional
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao) {

		log.info("transferir {}, valorMovimentacao {}, contaDestino {}", conta, valorMovimentacao, contaMovimentacao.getNumeroConta());

		final var valorSaldo = conta.getSaldo();

		final var valorSaldoDestino = contaMovimentacao.getSaldo();

		final var valorSaldoOriemAtualizado = calculaSaldoTransferenciaOrigem(valorSaldo, valorMovimentacao);

		validaValorSaldo(valorSaldoOriemAtualizado);

		conta.setSaldo(valorSaldoOriemAtualizado);

		conta = contaRepository.save(conta);

		final var valorSaldoDestinoAtualizado = calculaSaldoTransferenciaDestino(valorSaldoDestino, valorMovimentacao);

		contaMovimentacao.setSaldo(valorSaldoDestinoAtualizado);

		contaMovimentacao = contaRepository.save(contaMovimentacao);
		
		log.debug("{}, {}, valorSaldo {}, valorSaldoDestino {}, valorSaldoOriemAtualizado {}, valorSaldoDestinoAtualizado {}", 
				conta, 
				contaMovimentacao, 
				valorSaldo, 
				valorSaldoDestino, 
				valorSaldoOriemAtualizado, 
				valorSaldoDestinoAtualizado);
		
		final var transacaoConta = Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(valorSaldo)
				.valorMovimentacao(valorMovimentacao)
				.valorSaldoAtualizado(valorSaldoOriemAtualizado)
				.porcentagemMovimentacao(BigDecimal.ZERO)
				.valorTransacao(BigDecimal.ZERO)
				.conta(conta)
				.contaMovimentacao(contaMovimentacao)
				.tipoTransacao(new TipoTransacao(TRANSFERENCIA_SAIDA_DINHEIRO.getCodigo())).build();
		
		final var transacaoContaDestino = Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(valorSaldoDestino)
				.valorMovimentacao(valorMovimentacao)
				.valorSaldoAtualizado(valorSaldoDestinoAtualizado)
				.porcentagemMovimentacao(BigDecimal.ZERO)
				.valorTransacao(BigDecimal.ZERO)
				.conta(contaMovimentacao)
				.contaMovimentacao(conta)
				.tipoTransacao(new TipoTransacao(TRANSFERENCIA_ENTRADA_DINHEIRO.getCodigo())).build();
		
		transacaoRepository.saveAll(Arrays.asList( transacaoConta, transacaoContaDestino ) );

		log.debug("Transferencia efetuado com sucesso da {} para destino {} no valor de {}", conta, contaMovimentacao, valorMovimentacao);

		return conta;
	}

	private BigDecimal calculaSaldoTransferenciaOrigem(BigDecimal valorSaldo, BigDecimal valorMovimentacao) {

		log.info("calculaSaldoTransferenciaOrigem valorSaldo {}, valorMovimentacao {}", valorSaldo, valorMovimentacao);

		final var saldoAtualizado = valorSaldo.subtract(valorMovimentacao);

		log.debug("saldoAtualizado {}", saldoAtualizado);

		return saldoAtualizado;
	}

	private BigDecimal calculaSaldoTransferenciaDestino(BigDecimal saldo, BigDecimal valorMovimentacao) {

		log.info("calculaSaldoTransferenciaDestino saldo {}, valorMovimentacao {}", saldo, valorMovimentacao);

		final var saldoAtualizado = saldo.add(valorMovimentacao).setScale(2, RoundingMode.HALF_UP);

		log.debug("saldoAtualizado {}", saldoAtualizado);

		return saldoAtualizado;
	}

}