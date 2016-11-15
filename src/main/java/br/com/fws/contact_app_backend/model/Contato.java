package br.com.fws.contact_app_backend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Contato {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Endereco endereco;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private List<Telefone> telefones  = new ArrayList<>();;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})	
	private List<PerfilSocial> perfisSociais = new ArrayList<>();
	
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
	
	public List<Telefone> getTelefones() {
		
		List<Telefone> result = new ArrayList<>();
		
		Collections.copy(result , telefones);
		
		return result;
	}
	
	public void setTelefones(List<Telefone> telefone) {
		this.telefones = telefone;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public List<PerfilSocial> getPerfisSociais() {
		List<PerfilSocial> result = new ArrayList<>();
		
		Collections.copy(result , perfisSociais);
		
		return result;
	}	
	
	public void add(Telefone telefone) {
		telefones.add(telefone);		
	}
	
	public void add(PerfilSocial perfilSocial){
		perfisSociais.add(perfilSocial);
	}
	
	
}
