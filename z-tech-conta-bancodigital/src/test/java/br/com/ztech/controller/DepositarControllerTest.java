package br.com.ztech.controller;

import static br.com.ztech.eum.TipoTransacaoEnum.DEPOSITO_DINHEIRO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.ztech.controller.dto.ContaDto;
import br.com.ztech.controller.request.AbrirContaRequest;
import br.com.ztech.controller.request.MovimentacaoContaRequest;
import br.com.ztech.domain.TipoTransacao;
import br.com.ztech.repository.TipoTransacaoRepository;

@Sql(scripts = "classpath:delete_all_junit.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepositarControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;
	
	@MockBean
	private TipoTransacaoRepository tipoTransacaoRepository;
	
	@Test
	public void  depositarTest() {
		
		var abrirContaRequest = new AbrirContaRequest("Alvaro Duarte", "98933075038");

		var requestEntityAbrirConta = new HttpEntity<>(abrirContaRequest);

		var responseAbrirConta = restTemplate.exchange(
				"/conta",
				HttpMethod.POST,
				requestEntityAbrirConta,
				ContaDto.class);
		
		Assertions.assertThat(responseAbrirConta.getStatusCodeValue()).isEqualTo(201);
		Assertions.assertThat(responseAbrirConta.getBody().getCliente().getCpf()).isEqualTo("98933075038");
		Assertions.assertThat(responseAbrirConta.getBody().getCliente().getNome()).isEqualTo("Alvaro Duarte");
		
		var conta = responseAbrirConta.getBody();
		
		var tipoTransacao = Optional.of(new TipoTransacao(DEPOSITO_DINHEIRO.getCodigo(), "DEPOSITO DINHEIRO", new BigDecimal(0.50)));
		BDDMockito.when(tipoTransacaoRepository.findById(DEPOSITO_DINHEIRO.getCodigo())).thenReturn(tipoTransacao);	
		
		var movimentacaoContaRequest = new MovimentacaoContaRequest(new BigDecimal(500.00));
		movimentacaoContaRequest.setValor(new BigDecimal(500.00));
		
		var requestEntityDespositarConta = new HttpEntity<>(movimentacaoContaRequest);

		var responseDepositarConta = restTemplate.exchange(
				"/conta/depositar/"+conta.getAgencia()+"/"+conta.getConta(),
				HttpMethod.PUT,
				requestEntityDespositarConta,
				ContaDto.class);
	
		Assertions.assertThat(responseDepositarConta.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(responseDepositarConta.getBody().getCliente().getCpf()).isEqualTo("98933075038");
		Assertions.assertThat(responseDepositarConta.getBody().getCliente().getNome()).isEqualTo("Alvaro Duarte");
		
		Assertions.assertThat(responseDepositarConta.getBody().getSaldo().setScale(2, RoundingMode.HALF_UP))
		.isEqualTo(new BigDecimal(502.5).setScale(2, RoundingMode.HALF_UP));
		
	}

}