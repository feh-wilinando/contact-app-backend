package br.com.fws.contact_app_backend.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.fws.contact_app_backend.model.PerfilSocial;

public class PerfilSocialDao {
	
	@PersistenceContext
	private EntityManager manager;

	public void delete(PerfilSocial perfilSocial) {
		manager.remove(perfilSocial);
	}

	public void update(PerfilSocial perfilSocial) {
		manager.merge(perfilSocial);
	}
	
	public void add(PerfilSocial perfilSocial){
		manager.persist(perfilSocial);
	}

}
