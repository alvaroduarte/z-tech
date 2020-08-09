package br.com.ztech.service;

import static org.junit.Assert.assertEquals;
import static br.com.ztech.eum.TipoTransacaoEnum.RETIRAR_DINHEIRO;
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
import br.com.ztech.domain.Conta;
import br.com.ztech.domain.TipoTransacao;
import br.com.ztech.domain.Transacao;
import br.com.ztech.repository.TipoTransacaoRepository;
import br.com.ztech.repository.TransacaoRepository;

@RunWith(SpringRunner.class)
public class TransferirServiceTest {
	
	@Mock
	private ContaService contaService;
	
	@Mock
	private TipoTransacaoService tipoTransacaoService;

	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private TipoTransacaoRepository tipoTransacaoRepository;
	
	@InjectMocks
    private TransferirService transferirService;
	
	@Test
	public void depositarTest() {
		
		final var conta = new Conta(Integer.valueOf(100), Long.valueOf(10378), new Cliente("Alvaro Duarte", "98933075038"));
		conta.setSaldo(new BigDecimal(300));
		
		final var contaDestino = new Conta(Integer.valueOf(100), Long.valueOf(10379), new Cliente("Alvaro Duarte", "98933075038"));
		contaDestino.setSaldo(new BigDecimal(100));
		
		final var transacao =  Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(new BigDecimal(300))
				.valorMovimentacao(new BigDecimal(100))
				.porcentagemMovimentacao(BigDecimal.ZERO)
				.valorTransacao(new BigDecimal(100))
				.valorSaldoAtualizado(new BigDecimal(200))
				.conta(conta)
				.build();
		
		final var tipoTransacao = Optional.of(new TipoTransacao(RETIRAR_DINHEIRO.getCodigo(), "RETIRAR_DINHEIRO", new BigDecimal(1)));
		BDDMockito.when(tipoTransacaoRepository.findById(RETIRAR_DINHEIRO.getCodigo())).thenReturn(tipoTransacao);	
		
		BDDMockito.when(contaService.salvar(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
		
		var contaRetorno = transferirService.movimentacao(conta, new BigDecimal(100), contaDestino);
				
		Assertions.assertThat(contaRetorno.getSaldo()).isNotNull();
		assertEquals(contaRetorno.getSaldo().setScale(2, RoundingMode.HALF_UP), transacao.getValorSaldoAtualizado().setScale(2, RoundingMode.HALF_UP));
	}

}