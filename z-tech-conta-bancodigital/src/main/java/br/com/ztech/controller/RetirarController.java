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
import br.com.ztech.service.RetirarMovimentacaoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("conta")
public class RetirarController {
	
	@Autowired
	private RetirarMovimentacaoService retirarMovimentacaoService;
	
	@Autowired
	private ContaConverterContaDto contaConverterContaDto;
	
	@PutMapping("/retirar/{agencia}/{numeroConta}")
	public ResponseEntity<ContaDto> retirar(@PathVariable Integer agencia, 
			@PathVariable Long numeroConta, 
				@RequestBody @Valid MovimentacaoContaRequest movimentacaoContaRequest) {
		
		log.info("retirar agencia {}, numeroConta {}, {}", agencia, numeroConta, movimentacaoContaRequest);
				
		final var valorMovimentacao = movimentacaoContaRequest.getValor();
		
		final var conta = retirarMovimentacaoService.movimentacao(agencia, numeroConta, valorMovimentacao);
		
		log.debug("{}", conta);
				
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
	
	@PutMapping("/retirar/{id}")
	public ResponseEntity<ContaDto> retirar(@PathVariable Long id,
			@RequestBody @Valid MovimentacaoContaRequest movimentacaoContaRequest) {
		
		log.info("retirar id {}, {}", id, movimentacaoContaRequest);
		
		final var valorMovimentacao = movimentacaoContaRequest.getValor();
		
		final var conta = retirarMovimentacaoService.movimentacao(id, valorMovimentacao);
		
		log.debug("{}", conta);
				
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
}