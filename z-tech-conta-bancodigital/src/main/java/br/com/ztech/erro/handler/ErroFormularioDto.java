package br.com.ztech.erro.handler;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ErroFormularioDto {
	
	private LocalDateTime timestamp;
	private String status;
	private Integer error;
	private String message;
	private String path;
	
	public ErroFormularioDto(String status, Integer error, String message, String path) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	
	}
}