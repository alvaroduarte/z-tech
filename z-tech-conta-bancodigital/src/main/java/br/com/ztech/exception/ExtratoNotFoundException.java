package br.com.ztech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Não existe transação bancaria para essa conta!")
public class ExtratoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7227212838595641219L;
	
	public ExtratoNotFoundException() {
        super();
    }
    public ExtratoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ExtratoNotFoundException(String message) {
        super(message);
    }
    public ExtratoNotFoundException(Throwable cause) {
        super(cause);
    }
}