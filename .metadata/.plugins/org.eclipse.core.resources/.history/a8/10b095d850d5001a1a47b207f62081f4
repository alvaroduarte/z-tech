package br.com.ztech.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Conta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private Integer agencia;
	
	@Column(nullable=false)
	private Integer numeroConta;
	
	@Column(nullable=false)
	private BigDecimal saldo;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false) 
	private Cliente cliente;
	
	@OneToMany(mappedBy = "conta", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Transacao> transacoes;
	
	public Conta() {}
	
	public Conta(Long id, Integer agencia, Integer numeroConta, BigDecimal saldo, Cliente cliente) {
		this.id = id;
		this.agencia = agencia;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
		this.cliente = cliente;
	}
	
	public Conta(Integer agencia, Integer numeroConta, BigDecimal saldo, Cliente cliente) {
		this.agencia = agencia;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
		this.cliente = cliente;
	}

	public Conta(Integer agencia, BigDecimal saldo, Cliente cliente) {
		this.agencia = agencia;
		this.saldo = saldo;
		this.cliente = cliente;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Conta [id=" + id + ", agencia=" + agencia + ", numeroConta=" + numeroConta + ", saldo=" + saldo + ", cliente="
				+ cliente + ", transacoes=" + transacoes + "]";
	}
}