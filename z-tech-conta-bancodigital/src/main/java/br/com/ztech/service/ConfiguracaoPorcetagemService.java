package br.com.ztech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ztech.domain.ConfiguracaoPorcentagem;
import br.com.ztech.exception.ConfiguracaoPorcentagemNotFoundException;
import br.com.ztech.repository.ConfiguracaoPorcentagemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfiguracaoPorcetagemService {
			
	@Autowired
	private ConfiguracaoPorcentagemRepository configuracaoPorcentagemRepository;

	
	protected ConfiguracaoPorcentagem buscarConfiguracaoPorcentagem(Integer id) {
		
		log.info("buscarConfiguracaoPorcentagem {}", id);
		
		return configuracaoPorcentagemRepository.findById(id)
			.orElseThrow(() -> new ConfiguracaoPorcentagemNotFoundException());
		
	}
	
}