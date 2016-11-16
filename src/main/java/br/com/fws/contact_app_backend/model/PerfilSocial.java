package br.com.fws.contact_app_backend.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PerfilSocial {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String perfil;
	
	@Enumerated(EnumType.STRING)
	private RedeSocial redeSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public RedeSocial getRedeSocial() {
		return redeSocial;
	}

	public void setRedeSocial(RedeSocial redeSocial) {
		this.redeSocial = redeSocial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		result = prime * result + ((redeSocial == null) ? 0 : redeSocial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilSocial other = (PerfilSocial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (perfil == null) {
			if (other.perfil != null)
				return false;
		} else if (!perfil.equals(other.perfil))
			return false;
		if (redeSocial != other.redeSocial)
			return false;
		return true;
	}
	
	enum RedeSocial {

		TWITTER,INSTAGRAM,GOOGLE_PLUS,FACEBOOK;
		
	}
	
}
