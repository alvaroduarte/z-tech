package br.com.ztech.controller.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
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
@ToString
public class ContaDto {

	private Long id;
	private ClienteDto cliente;
	private Integer agencia;
	private Long conta;
	private BigDecimal saldo;

}