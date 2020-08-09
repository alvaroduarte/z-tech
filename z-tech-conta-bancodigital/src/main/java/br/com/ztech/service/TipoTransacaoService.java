package br.com.ztech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ztech.domain.TipoTransacao;
import br.com.ztech.exception.TipoTransacaoNotFoundException;
import br.com.ztech.repository.TipoTransacaoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TipoTransacaoService {
			
	@Autowired
	private TipoTransacaoRepository tipoTransacaoRepository;

	protected TipoTransacao burcarPorId(Integer id) {
		
		log.info("burcarPorId {}", id);
				
		return tipoTransacaoRepository.findById(id)
			.orElseThrow(() -> new TipoTransacaoNotFoundException());
		
	}
	
}