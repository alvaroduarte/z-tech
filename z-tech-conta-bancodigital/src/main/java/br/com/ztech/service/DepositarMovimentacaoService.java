package br.com.ztech.service;

import static br.com.ztech.eum.TipoTransacaoEnum.DEPOSITO_DINHEIRO;

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
public class DepositarMovimentacaoService implements Movimentacao {

	@Autowired
	private ContaService contaService;
	
	@Autowired
	private TipoTransacaoService tipoTransacaoService;

	@Autowired
	private TransacaoRepository transacaoRepository;

	public Conta movimentacao(Integer agencia, Long numeroConta, BigDecimal valorMovimentacao) {
		
		final var conta = contaService.buscarConta(agencia, numeroConta);
		
		return movimentacao( conta , valorMovimentacao, null );
	
	}
	
	public Conta movimentacao(Long id, BigDecimal valorMovimentacao) {
		
		final var conta = contaService.buscarContaPorId(id);
		
		return movimentacao( conta , valorMovimentacao, null );
	
	}

	@Transactional
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaTransacao) {

		log.info("movimentacao {}, valorMovimentacao {}", conta, valorMovimentacao);
		
		final var tipotransacao = tipoTransacaoService.burcarPorId(DEPOSITO_DINHEIRO.getCodigo());
		
		final var porcentagemBonusDeposito =  tipotransacao.getPorcentagem();

		final var valorSaldo = conta.getSaldo();

		final var valorBonus = calculoPorcentagem(porcentagemBonusDeposito, valorMovimentacao);

		final var valorSaldoAtualizado = calculaSoma(valorSaldo, valorMovimentacao, valorBonus);
		
		final var valorTransacao = calculoValorTransacao(valorMovimentacao, valorBonus); 

		log.debug("{}, porcentagemBonusDeposito {}, valorSaldo {}, valorBonus {}, valorTransacao {}, valorSaldoAtualizado {}", 
				conta, 
				porcentagemBonusDeposito, 
				valorSaldo, 
				valorBonus, 
				valorTransacao,
				valorSaldoAtualizado);

		conta.setSaldo( valorSaldoAtualizado );

		conta = contaService.salvar(conta);
		
		final var transacao = Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(valorSaldo)
				.valorMovimentacao(valorMovimentacao)
				.porcentagemMovimentacao(porcentagemBonusDeposito)
				.valorTransacao(valorTransacao)
				.valorSaldoAtualizado(valorSaldoAtualizado)
				.conta(conta)
				.tipoTransacao(tipotransacao)
				.build();
		
		transacaoRepository.save(transacao);	
		
		log.debug("deposito efetuado com sucesso na {}", conta);

		return conta;
	}
	
}