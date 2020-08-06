package br.com.ztech.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class ExtratoDto {
	
	private LocalDateTime data;
	private BigDecimal valorSaldo;
	private BigDecimal valorMovimentacao;
	private BigDecimal porcentagemMovimentacao;
	private BigDecimal valorTransacao;
	private BigDecimal valorSaldoAtualizado;
	private TipoExtratoDto tipoTransacao;
	private ContaExtratoDto contaTrasancao;
	
}	