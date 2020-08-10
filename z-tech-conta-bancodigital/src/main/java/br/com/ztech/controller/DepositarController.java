package br.com.ztech.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ztech.controller.dto.ContaDto;
import br.com.ztech.controller.dto.converter.ContaConverterContaDto;
import br.com.ztech.controller.request.MovimentacaoContaRequest;
import br.com.ztech.service.DepositarMovimentacaoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("conta")
public class DepositarController {
	
	@Autowired
	private DepositarMovimentacaoService depositarMovimentacaoService;
	
	@Autowired
	private ContaConverterContaDto contaConverterContaDto;
	
	@PutMapping("/depositar/{agencia}/{numeroConta}")
	public ResponseEntity<ContaDto> depositar(@PathVariable Integer agencia, 
			@PathVariable Long numeroConta, @RequestBody @Valid MovimentacaoContaRequest movimentacaoContaRequest) {
		
		log.info("depositar agencia {}, numeroConta {}, {}", agencia, numeroConta, movimentacaoContaRequest);		
		
		final var valorMovimentacao = movimentacaoContaRequest.getValor();
		
		final var conta = depositarMovimentacaoService.movimentacao(agencia, numeroConta, valorMovimentacao);
		
		log.debug("deposito efetuado com sucesso! {}", conta);
				
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
	
	@PutMapping("/depositar/{id}")
	public ResponseEntity<ContaDto> depositar(@PathVariable Long id, 
			@RequestBody @Valid MovimentacaoContaRequest movimentacaoContaRequest) {
		
		log.info("depositar id {}, {}", id, movimentacaoContaRequest);
		
		final var valorMovimentacao = movimentacaoContaRequest.getValor();
		
		final var conta = depositarMovimentacaoService.movimentacao(id, valorMovimentacao);
		
		log.debug("deposito efetuado com sucesso! {}", conta);
		
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
}