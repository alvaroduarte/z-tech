package br.com.ztech.service;

import static br.com.ztech.eum.ConfiguracaoPorcentagemEnum.CUSTO_RETIRADA;
import static org.junit.Assert.assertEquals;

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
import br.com.ztech.domain.ConfiguracaoPorcentagem;
import br.com.ztech.domain.Conta;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.TransacaoRepository;

@RunWith(SpringRunner.class)
public class RetirarServiceTest {
	
	@Mock
	private ContaService contaService;
	
	@Mock
	private ConfiguracaoPorcetagemService configuracaoPorcetagemService;
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@InjectMocks
    private RetirarService retirarService;
	
	@Test
	public void depositarTest() {
		
		final var conta = new Conta(Integer.valueOf(100), Long.valueOf(10378), new Cliente("Alvaro Duarte", "98933075038"));
		conta.setSaldo(new BigDecimal(300));
		
		final var transacao =  Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(new BigDecimal(300))
				.valorMovimentacao(new BigDecimal(100))
				.porcentagemMovimentacao(new BigDecimal(-1))
				.valorTransacao(new BigDecimal(99))
				.valorSaldoAtualizado(new BigDecimal(199))
				.conta(conta)
				.build();	
		
		final var configuracaoPorcentagem = new ConfiguracaoPorcentagem(CUSTO_RETIRADA.getValor(), "CUSTO_RETIRADA", new BigDecimal(-1));
		BDDMockito.when( configuracaoPorcetagemService.buscarConfiguracaoPorcentagem(CUSTO_RETIRADA.getValor()) ).thenReturn(configuracaoPorcentagem);	
		
		BDDMockito.when(contaService.buscarContaPorId(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(contaService.buscarConta(Mockito.any(), Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(contaService.salvar(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
		
		var contaRetorno = retirarService.movimentacao(conta.getId(), new BigDecimal(100));
				
		Assertions.assertThat(contaRetorno.getSaldo()).isNotNull();
		assertEquals(contaRetorno.getSaldo().setScale(2, RoundingMode.HALF_UP), transacao.getValorSaldoAtualizado().setScale(2, RoundingMode.HALF_UP));
	}

}