package petz.com.br.controller.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClienteRequest {
	
	private Long id;
	
	@NotNull
	@NotEmpty
	private String nome;
	
	@NotNull
	private Long cpf;
	
	private Long celular;
    
	@NotNull
	@NotEmpty
	private List<PetRequest> pets;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public Long getCelular() {
		return celular;
	}
	public void setCelular(Long celular) {
		this.celular = celular;
	}
	public List<PetRequest> getPets() {
		return pets;
	}
	public void setPets(List<PetRequest> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return "ClienteRequest [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", celular=" + celular + ", pets="
				+ pets + "]";
	}	
}