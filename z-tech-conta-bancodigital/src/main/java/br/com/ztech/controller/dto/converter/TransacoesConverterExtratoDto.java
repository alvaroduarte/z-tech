package br.com.ztech.controller.dto.converter;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import br.com.ztech.controller.dto.ClienteDto;
import br.com.ztech.controller.dto.ContaExtratoDto;
import br.com.ztech.controller.dto.ExtratoDto;
import br.com.ztech.controller.dto.TipoExtratoDto;
import br.com.ztech.domain.Transacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TransacoesConverterExtratoDto implements Converter<Page<Transacao>, Page<ExtratoDto>> {

	@Override
	public Page<ExtratoDto> convert(Page<Transacao> source) {

		log.debug("TransacoesConverterExtratoDto convert {}", source);
		
		return new PageImpl<ExtratoDto>(
				 source
				.stream()
				.map(t -> ExtratoDto.builder()
					.data(t.getData())
					.valorSaldo(t.getValorSaldo())
					.valorMovimentacao(t.getValorMovimentacao())
					.porcentagemMovimentacao(t.getPorcentagemMovimentacao())
					.valorTransacao(t.getValorTransacao())
					.valorSaldoAtualizado(t.getValorSaldoAtualizado())
					.tipoTransacao(new TipoExtratoDto(t.getTipoTransacao().getNome()))
					.contaTrasancao(getContaExtratoDto(t))
					.build())
				    .collect(Collectors.toList()), source.getPageable(), source.getTotalElements());
	}
	
	private ContaExtratoDto getContaExtratoDto(Transacao t) {

		if(Objects.nonNull(t.getContaTrasancao())) {

			return new ContaExtratoDto(
					
					new ClienteDto(
					
							t.getContaTrasancao().getCliente().getNome(), 
							t.getContaTrasancao().getCliente().getCpf()),  
					
					t.getContaTrasancao().getAgencia(), 
					t.getContaTrasancao().getNumeroConta());

		}

		return null;
	}
}