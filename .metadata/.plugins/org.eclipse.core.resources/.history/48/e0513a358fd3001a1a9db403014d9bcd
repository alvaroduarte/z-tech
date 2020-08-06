package petz.com.br.controller.dto.converter;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import petz.com.br.controller.dto.ClienteDto;
import petz.com.br.domain.Cliente;

@Configuration
public class ClienteConverterClienteDto implements Converter<Cliente, ClienteDto> {

	@Override
	public ClienteDto convert(Cliente source) {
		
		return  new ClienteDto( source );
	}
	
}