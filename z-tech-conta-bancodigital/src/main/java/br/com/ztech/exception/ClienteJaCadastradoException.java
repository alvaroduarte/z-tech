package br.com.ztech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Cliente ja possui conta aberta na z-tech banco digital!")
public class ClienteJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 7227212838595641219L;
	
	public ClienteJaCadastradoException() {
        super();
    }
    public ClienteJaCadastradoException(String message, Throwable cause) {
        super(message, cause);
    }
    public ClienteJaCadastradoException(String message) {
        super(message);
    }
    public ClienteJaCadastradoException(Throwable cause) {
        super(cause);
    }
}