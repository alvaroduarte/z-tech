package br.com.ztech.controller.request;

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
public class MovimentacaoContaRequest  {
	
	@NotNull
	private BigDecimal valor;
		
	public MovimentacaoContaRequest() {}

}