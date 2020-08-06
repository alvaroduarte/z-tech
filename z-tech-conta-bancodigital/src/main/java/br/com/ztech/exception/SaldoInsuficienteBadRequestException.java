package br.com.ztech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "A Conta não tem saldo suficiente para efetuar essa transação!")
public class SaldoInsuficienteBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 7227212838595641219L;
	
	public SaldoInsuficienteBadRequestException() {
        super();
    }
    public SaldoInsuficienteBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public SaldoInsuficienteBadRequestException(String message) {
        super(message);
    }
    public SaldoInsuficienteBadRequestException(Throwable cause) {
        super(cause);
    }
}