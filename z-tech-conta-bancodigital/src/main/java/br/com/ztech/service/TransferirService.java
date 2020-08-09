package br.com.ztech.service;

import static br.com.ztech.eum.TipoTransacaoEnum.TRANSFERENCIA_ENTRADA_DINHEIRO;
import static br.com.ztech.eum.TipoTransacaoEnum.TRANSFERENCIA_SAIDA_DINHEIRO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ztech.domain.Conta;
import br.com.ztech.domain.TipoTransacao;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.TransacaoRepository;
import br.com.ztech.service.strategy.Movimentacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransferirService implements Movimentacao {

	@Autowired
	private ContaService contaService;

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public Conta movimentacao(Integer agencia, Long numeroConta, BigDecimal valorMovimentacao, Integer agenciaDestino, Long numeroContaDestino) {
		
		final var conta = contaService.buscarConta(agencia, numeroConta);
		
		final var contaDestino = contaService.buscarConta(agenciaDestino, numeroContaDestino);
		
		return movimentacao(conta, valorMovimentacao, contaDestino);
	
	}
	
	public Conta movimentacao(Long id, BigDecimal valorMovimentacao, Integer agenciaDestino, Long numeroContaDestino) {
	
		final var conta = contaService.buscarContaPorId(id);
		
		final var contaDestino = contaService.buscarConta(agenciaDestino, numeroContaDestino);
		
		return movimentacao(conta, valorMovimentacao, contaDestino);
	
	}

	@Transactional
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao) {

		log.info("transferir {}, valorMovimentacao {}, contaDestino {}", conta, valorMovimentacao, contaMovimentacao.getNumeroConta());

		final var valorSaldo = conta.getSaldo();

		final var valorSaldoDestino = contaMovimentacao.getSaldo();

		final var valorSaldoOriemAtualizado = calculaRetirada(valorSaldo, valorMovimentacao, BigDecimal.ZERO);

		contaService.validaValorSaldoConta(valorSaldoOriemAtualizado);

		conta.setSaldo(valorSaldoOriemAtualizado);

		conta = contaService.salvar(conta);

		final var valorSaldoDestinoAtualizado = calculaSoma(valorSaldoDestino, valorMovimentacao, BigDecimal.ZERO);

		contaMovimentacao.setSaldo(valorSaldoDestinoAtualizado);

		contaMovimentacao = contaService.salvar(contaMovimentacao);
		
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
				.valorTransacao(valorMovimentacao)
				.conta(conta)
				.contaMovimentacao(contaMovimentacao)
				.tipoTransacao(new TipoTransacao(TRANSFERENCIA_SAIDA_DINHEIRO.getCodigo())).build();
		
		final var transacaoContaDestino = Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(valorSaldoDestino)
				.valorMovimentacao(valorMovimentacao)
				.valorSaldoAtualizado(valorSaldoDestinoAtualizado)
				.porcentagemMovimentacao(BigDecimal.ZERO)
				.valorTransacao(valorMovimentacao)
				.conta(contaMovimentacao)
				.contaMovimentacao(conta)
				.tipoTransacao(new TipoTransacao(TRANSFERENCIA_ENTRADA_DINHEIRO.getCodigo())).build();
		
		transacaoRepository.saveAll(Arrays.asList( transacaoConta, transacaoContaDestino ) );

		log.debug("Transferencia efetuado com sucesso da {} para destino {} no valor de {}", conta, contaMovimentacao, valorMovimentacao);

		return conta;
	}

}