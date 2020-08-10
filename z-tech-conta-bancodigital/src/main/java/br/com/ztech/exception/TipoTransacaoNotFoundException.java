package br.com.ztech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Não foi possivel localizar dados nas entidades basicas do sistema! Entidade TipoTransacao! ADM veja o script esta em src/main/resources!")
public class TipoTransacaoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7227212838595641219L;
	
	public TipoTransacaoNotFoundException() {
        super();
    }
    public TipoTransacaoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public TipoTransacaoNotFoundException(String message) {
        super(message);
    }
    public TipoTransacaoNotFoundException(Throwable cause) {
        super(cause);
    }
}