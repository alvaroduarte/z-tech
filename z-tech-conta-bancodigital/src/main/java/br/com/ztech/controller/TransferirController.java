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
import br.com.ztech.controller.request.TransferenciaContaRequest;
import br.com.ztech.service.TransferirService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("conta")
public class TransferirController {
		
	@Autowired
	private TransferirService transferirService;
	
	@Autowired
	private ContaConverterContaDto contaConverterContaDto;
	
		
	@PutMapping("/transferir/{agencia}/{numeroConta}")
	public ResponseEntity<ContaDto> transferir(@PathVariable Integer agencia, 
			@PathVariable Long numeroConta, 
				@RequestBody @Valid TransferenciaContaRequest transferenciaContaRequest) {
		
		log.info("transferir agencia {}, conta {}, {}", agencia, numeroConta, transferenciaContaRequest);
		
		final var valorMovimentacao = transferenciaContaRequest.getValor();
		
		final var conta = transferirService.transferir(agencia, numeroConta, 
				valorMovimentacao, transferenciaContaRequest.getAgencia(), transferenciaContaRequest.getConta());
		
		log.debug("{}", conta);
						
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
	
	@PutMapping("/transferir/{id}")
	public ResponseEntity<ContaDto> transferir(@PathVariable Long id, 
			@RequestBody @Valid TransferenciaContaRequest transferenciaContaRequest) {
		
		log.info("transferir id {}, {}", id, transferenciaContaRequest);
		
		final var valorMovimentacao = transferenciaContaRequest.getValor();
		
		final var conta = transferirService.transferir(id, valorMovimentacao, 
				transferenciaContaRequest.getAgencia(), transferenciaContaRequest.getConta());
		
		log.debug("{}", conta);
		
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
}