package br.com.ztech.controller.request.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import br.com.ztech.controller.request.AbrirContaRequest;
import br.com.ztech.domain.Cliente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AbrirContaRequestConverterCliente implements Converter<AbrirContaRequest, Cliente> {
	
	@Override
	public Cliente convert(AbrirContaRequest source) {
		
		log.info("AbrirContaRequestConverterCliente convert {}", source);
		
		final var cliente = new Cliente(source.getNome(), source.getCpf().replaceAll("[.-]", ""));
		
		log.info("{}", cliente);
		
		return cliente;
	}
}