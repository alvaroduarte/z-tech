package br.com.ztech.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TipoTransacao {
	
	@Id
	private Integer id;
	
	@Column(nullable=false)
	private String nome;
		
	@Column(nullable=false) 
	private BigDecimal porcentagem;

	
	public TipoTransacao(Integer id) {
		this.id = id;
	}
	
}