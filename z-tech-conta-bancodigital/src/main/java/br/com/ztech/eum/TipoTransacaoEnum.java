package br.com.ztech.eum;

public enum TipoTransacaoEnum {

	DEPOSITO_DINHEIRO(1),
	RETIRAR_DINHEIRO(2),
	TRANSFERENCIA_SAIDA_DINHEIRO(3),
	TRANSFERENCIA_ENTRADA_DINHEIRO(4);

	private Integer codigo;

	TipoTransacaoEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}
}