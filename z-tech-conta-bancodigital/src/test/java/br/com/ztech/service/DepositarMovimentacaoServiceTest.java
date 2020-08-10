package br.com.ztech.service;

import static br.com.ztech.eum.TipoTransacaoEnum.DEPOSITO_DINHEIRO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ztech.domain.Cliente;
import br.com.ztech.domain.Conta;
import br.com.ztech.domain.TipoTransacao;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.TransacaoRepository;

@RunWith(SpringRunner.class)
public class DepositarMovimentacaoServiceTest {
	
	@Mock
	private ContaService contaService;
	
	@Mock
	private TipoTransacaoService tipoTransacaoService;

	@Mock
	private TransacaoRepository transacaoRepository;
	
	@InjectMocks
    private DepositarMovimentacaoService depositarMovimentacaoService;
	
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
		
		final var tipoTransacao = new TipoTransacao(DEPOSITO_DINHEIRO.getCodigo(), "DEPOSITO DINHEIRO", new BigDecimal(0.05));
		BDDMockito.when( tipoTransacaoService.burcarPorId(DEPOSITO_DINHEIRO.getCodigo()) ).thenReturn(tipoTransacao);	
		
		BDDMockito.when(contaService.buscarContaPorId(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(contaService.buscarConta(Mockito.any(), Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(contaService.salvar(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
		
		final var contaRtorno = depositarMovimentacaoService.movimentacao(conta.getId(), new BigDecimal(100));
				
		Assertions.assertThat(contaRtorno.getSaldo()).isNotNull();
		assertEquals(contaRtorno.getSaldo().setScale(2, RoundingMode.HALF_UP), transacao.getValorSaldoAtualizado().setScale(2, RoundingMode.HALF_UP));
	}

}