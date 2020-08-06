package br.com.ztech.eum;

public enum AgenciaEnum {

	AGENCIA_PADRAO(100);

	private Integer codigo;

	AgenciaEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}
}