package br.com.ztech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Não foi possivel localizar as configurações basica do sistema, favor da carga na entidade configuracao_porcentagem! O Script esta em src/main/resources!")
public class ConfiguracaoPorcentagemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7227212838595641219L;
	
	public ConfiguracaoPorcentagemNotFoundException() {
        super();
    }
    public ConfiguracaoPorcentagemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ConfiguracaoPorcentagemNotFoundException(String message) {
        super(message);
    }
    public ConfiguracaoPorcentagemNotFoundException(Throwable cause) {
        super(cause);
    }
}