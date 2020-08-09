package br.com.ztech.service;

import static br.com.ztech.eum.ConfiguracaoPorcentagemEnum.BONUS_DEPOSITO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ztech.domain.Cliente;
import br.com.ztech.domain.ConfiguracaoPorcentagem;
import br.com.ztech.domain.Conta;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.ConfiguracaoPorcentagemRepository;
import br.com.ztech.repository.ContaRepository;
import br.com.ztech.repository.TransacaoRepository;

@RunWith(SpringRunner.class)
public class DepositarServiceTest {
	
	@Mock
	private ContaRepository contaRepository;

	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private ConfiguracaoPorcentagemRepository configuracaoPorcentagemRepository;
	
	@InjectMocks
    private DepositarService depositarService;
	
	@Test
	public void depositarTest() {
		
		final var conta = new Conta(Integer.valueOf(100), Long.valueOf(10378), new Cliente("Alvaro Duarte", "98933075038"));
		conta.setSaldo(new BigDecimal(100));
		
		final var transacao =  Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(new BigDecimal(100))
				.valorMovimentacao(new BigDecimal(10))
				.porcentagemMovimentacao(new BigDecimal(0.5))
				.valorTransacao(new BigDecimal(100.50))
				.valorSaldoAtualizado(new BigDecimal(200.05))
				.conta(conta)
				.build();
		
		final var configuracaoPorcentagem = Optional.of(new ConfiguracaoPorcentagem(BONUS_DEPOSITO.getValor(), "BONUS_DEPOSITO", new BigDecimal(0.05)));
		BDDMockito.when(configuracaoPorcentagemRepository.findById(BONUS_DEPOSITO.getValor())).thenReturn(configuracaoPorcentagem);	
		
		BDDMockito.when(contaRepository.findById(Mockito.any())).thenReturn(Optional.of(conta));
		
		BDDMockito.when(contaRepository.findByAgenciaAndNumeroConta(Mockito.any(), Mockito.any())).thenReturn(Optional.of(conta));
		
		BDDMockito.when(contaRepository.save(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
		
		final var contaRtorno = depositarService.depositar(conta.getId(), new BigDecimal(100));
				
		Assertions.assertThat(contaRtorno.getSaldo()).isNotNull();
		assertEquals(contaRtorno.getSaldo().setScale(2, RoundingMode.HALF_UP), transacao.getValorSaldoAtualizado().setScale(2, RoundingMode.HALF_UP));
	}

}