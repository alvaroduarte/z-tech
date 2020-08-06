package br.com.ztech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Cpf inválido!")
public class CpfInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 7227212838595641219L;
	
	public CpfInvalidoException() {
        super();
    }
    public CpfInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
    public CpfInvalidoException(String message) {
        super(message);
    }
    public CpfInvalidoException(Throwable cause) {
        super(cause);
    }
}