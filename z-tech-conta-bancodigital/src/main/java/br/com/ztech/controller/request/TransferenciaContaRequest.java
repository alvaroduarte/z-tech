package br.com.ztech.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class TransferenciaContaRequest implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3846877239901477753L;

	@NotNull
	private Integer agencia;
	
	@NotNull
	private Long conta;
	
	@NotNull
	private BigDecimal valor;
}