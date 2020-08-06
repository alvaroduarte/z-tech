package br.com.ztech.eum;

public enum ConfiguracaoPorcentagemEnum {

	BONUS_DEPOSITO(1),
	CUSTO_RETIRADA(2);
	
	private Integer valor;

	ConfiguracaoPorcentagemEnum(Integer valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}
}