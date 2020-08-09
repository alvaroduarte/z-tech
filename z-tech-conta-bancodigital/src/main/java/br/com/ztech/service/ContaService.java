package br.com.ztech.service;

import static br.com.ztech.eum.AgenciaEnum.AGENCIA_PADRAO;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ztech.domain.Cliente;
import br.com.ztech.domain.ConfiguracaoPorcentagem;
import br.com.ztech.domain.Conta;
import br.com.ztech.exception.ConfiguracaoPorcentagemNotFoundException;
import br.com.ztech.exception.ContaNotFoundException;
import br.com.ztech.exception.ContaPorIdNotFoundException;
import br.com.ztech.exception.SaldoInsuficienteBadRequestException;
import br.com.ztech.repository.ConfiguracaoPorcentagemRepository;
import br.com.ztech.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
			
	@Autowired
	private ConfiguracaoPorcentagemRepository configuracaoPorcentagemRepository;

	@Autowired
	private ClienteService clienteService;

	public Conta buscarContaPorId(Long id) {

		log.info("buscarConta id {}", id);
		
		return contaRepository.findById(id)
				.orElseThrow(() -> new ContaPorIdNotFoundException());
	}

	public Conta buscarConta(Integer agencia, Long numeroConta) {

		log.info("buscarConta agencia {}, numeroConta {}", agencia, numeroConta);

		return contaRepository.findByAgenciaAndNumeroConta(agencia, numeroConta)
				.orElseThrow(() -> new ContaNotFoundException());
	}

	@Transactional
	public Conta abrirConta(Cliente cliente) {

		log.info("abrirConta {}", cliente);
		
		cliente = clienteService.salvar(cliente);
		
		var numeroConta = contaRepository.getNumeroConta();

		final var conta = contaRepository.save(new Conta(AGENCIA_PADRAO.getCodigo(), numeroConta, cliente));

		log.debug("Conta aberta com suecesso! {}", conta);

		return conta;
	}
	
	protected ConfiguracaoPorcentagem buscarConfiguracaoPorcentagem(Integer id) {
		
		log.info("buscarConfiguracaoPorcentagem {}", id);
		
		return configuracaoPorcentagemRepository.findById(id)
			.orElseThrow(() -> new ConfiguracaoPorcentagemNotFoundException());
		
	}
	
	protected BigDecimal calculoValorTransacao(BigDecimal valorMovimentacao, BigDecimal valor) {

		log.info("calculoValorTransacao valorMovimentacao {}, valor {}", valorMovimentacao, valor);

		final var valorTransacao = valorMovimentacao.add(valor);

		log.debug("valorTransacao {}", valorTransacao);

		return valorTransacao;

	}
	
	protected BigDecimal calculoValorPorcentagem(BigDecimal valorPorcentagem, BigDecimal valor) {

		log.info("calculoValorPorcentagemRetirada valorPorcentagem {}, valor {}", valorPorcentagem, valor);
		
		if(valorPorcentagem.signum() > 0) {
		
			final var valorCalculado = valor.multiply(valorPorcentagem).divide(new BigDecimal(100));

			log.debug("valorCalculado {}", valorCalculado);
			
			return valorCalculado;
		}

		return BigDecimal.ZERO;
	}

	protected void validaValorSaldo(BigDecimal valor) {

		log.info("validaValorSaldo valor {}", valor);

		if(valor.signum() < 0){

			log.error("A Conta não tem saldo suficiente para efetuar essa transação!");

			throw new SaldoInsuficienteBadRequestException();
		}

	}
}