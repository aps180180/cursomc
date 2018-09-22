package com.andresantos.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.andresantos.cursomc.domain.Cliente;
import com.andresantos.cursomc.services.validation.utils.ClienteInsert;

@ClienteInsert
public class ClienteDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// nesse DTO não altera o CPF e nemo tipo do cliente
	private Integer id;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=5,max=120,message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Email(message="Email Inválido")
	private String email;
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Cliente obj) {
		id    = obj.getId();
		nome  = obj.getNome();
		email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
