package br.com.ztech.erro.handler;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroValidacaoHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErroFormularioDto handle(MethodArgumentNotValidException exception) {

		var message = exception.getBindingResult().getFieldErrors()
				.stream()
				.map( f -> f.getField() +" "+ messageSource.getMessage(f, LocaleContextHolder.getLocale()))
				.collect(Collectors.joining(", "));
		
		 
		return	new ErroFormularioDto(
						"Bad Request", 
						HttpStatus.BAD_REQUEST.value(), message, "/conta/");

	}
	
}