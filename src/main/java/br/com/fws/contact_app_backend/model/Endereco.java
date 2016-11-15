package br.com.fws.contact_app_backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String rua;
	private Integer numero;
	private String cep;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	
	
}
