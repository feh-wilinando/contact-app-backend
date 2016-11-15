package br.com.fws.contact_app_backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.fws.contact_app_backend.model.Contato;

public class ContatoDao {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Contato> listAll() {
		return manager.createQuery("select c from Contato c join fetch c.telefones t", Contato.class).getResultList();
	}

	
	public boolean hasId(Long id){				
		return  manager.find(Contato.class, id) != null;
	}
	
	public Contato findById(Long id) {
		return manager
				.createQuery("select c from Contato c join fetch c.telefones t where c.id = :id", Contato.class)
				.setParameter("id", id)
				.getSingleResult();
	}

	public void add(Contato contato) {
		manager.persist(contato);		
	}

	public void remove(Contato contato) {
		manager.remove(contato);		
	}


	public void update(Contato contato) {
		manager.merge(contato);
	}

}
