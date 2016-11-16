package br.com.fws.contact_app_backend.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.fws.contact_app_backend.model.Telefone;

public class TelefoneDao {
	
	@PersistenceContext
	private EntityManager manager;

	public void delete(Telefone telefone) {		
		manager.remove(telefone);		
	}

	public void update(Telefone telefone) {
		manager.merge(telefone);	
	}
	
	public void add(Telefone telefone){
		manager.persist(telefone);
	}
	
}
