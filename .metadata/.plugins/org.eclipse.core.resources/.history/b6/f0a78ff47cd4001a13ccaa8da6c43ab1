package br.com.ztech.controller.request.converter;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import br.com.ztech.controller.request.AbrirContaRequest;
import br.com.ztech.domain.Cliente;

@Configuration
public class AbrirContaRequestConverterCliente implements Converter<AbrirContaRequest, Cliente> {

	@Override
	public Cliente convert(AbrirContaRequest source) {
		
		Cliente cliente = new Cliente();
		cliente.setNome(source.getNome());
		cliente.setCpf(source.getCpf().replaceAll("[.-]", ""));
		
		return cliente;
	}
}