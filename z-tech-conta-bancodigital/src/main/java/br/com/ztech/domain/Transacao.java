package br.com.ztech.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//
//@AllArgsConstructor
//@NoArgsConstructor
//

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Transacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false)
	private LocalDateTime data;

	@Column(nullable=false)
	private BigDecimal valorSaldo;

	@Column(nullable=false)
	private BigDecimal valorMovimentacao;

	@Column(nullable=true)
	private BigDecimal porcentagemMovimentacao;

	@Column(nullable=true)
	private BigDecimal valorTransacao;

	@Column(nullable=false)
	private BigDecimal valorSaldoAtualizado;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "conta_id", nullable = false) 
	private Conta conta;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "contamovimentacao_id", nullable = true) 
	private Conta contaMovimentacao;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "tipotransacao_id", nullable = false) 
	private TipoTransacao tipoTransacao;
	
}