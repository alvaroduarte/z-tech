package br.com.ztech.service;

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
import br.com.ztech.domain.Conta;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.TipoTransacaoRepository;
import br.com.ztech.repository.TransacaoRepository;

@RunWith(SpringRunner.class)
public class TransferirMovimentacaoServiceTest {
	
	@Mock
	private ContaService contaService;
	
	@Mock
	private TipoTransacaoService tipoTransacaoService;

	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private TipoTransacaoRepository tipoTransacaoRepository;
	
	@InjectMocks
    private TransferirMovimentacaoService transferirMovimentacaoService;
	
	@Test
	public void depositarTest() {
		
		final var conta = new Conta(Integer.valueOf(100), Long.valueOf(10378), new Cliente("Alvaro Duarte", "98933075038"));
		conta.setSaldo(new BigDecimal(300));
		
		final var contaTransacao = new Conta(Integer.valueOf(100), Long.valueOf(10379), new Cliente("Alvaro Duarte", "98933075038"));
		contaTransacao.setSaldo(new BigDecimal(100));
		
		final var transacao =  Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(new BigDecimal(300))
				.valorMovimentacao(new BigDecimal(100))
				.porcentagemMovimentacao(BigDecimal.ZERO)
				.valorTransacao(new BigDecimal(100))
				.valorSaldoAtualizado(new BigDecimal(200))
				.conta(conta)
				.build();
		
		BDDMockito.when(contaService.salvar(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
		
		var contaRetorno = transferirMovimentacaoService.movimentacao(conta, new BigDecimal(100), contaTransacao);
				
		Assertions.assertThat(contaRetorno.getSaldo()).isNotNull();
		assertEquals(contaRetorno.getSaldo().setScale(2, RoundingMode.HALF_UP), transacao.getValorSaldoAtualizado().setScale(2, RoundingMode.HALF_UP));
	}

}