package com.magnabyte.cfdi.portal.model.commons;

import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusGenerico;
import com.magnabyte.cfdi.portal.model.establecimiento.Establecimiento;

/**
 * Clase que representa un usuario de establecimiento
 * 
 * @author Edgar Pérez
 *
 */
public class Usuario {

	/**
	 * Identificador unico de usuario
	 */
	private Integer id;
	
	/**
	 * Nombre de usuario
	 */
	private String usuario;
	
	/**
	 * Contraseña de usuario
	 */
	private String password;
	
	/**
	 * Establecimiento al que pertenece el usuario
	 */
	private Establecimiento establecimiento;
	
	/**
	 * Estatus del usuario
	 */
	private EstatusGenerico estatus;

	/**
	 * Obtiene el id de usuario
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Asigna el id de usuario
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de usuario
	 * @return usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Asigna el nombre de usuario
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene la contraselña de usiario
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Asigna la contraseña de usuario
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene el estableciemiento de usuario
	 * @return
	 */
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	/**
	 * Asigna el establecimiento de usuario
	 * @param establecimiento
	 */
	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	/**
	 * Obtiene el estatus de usuario
	 * @return estatus
	 */
	public EstatusGenerico getEstatus() {
		return estatus;
	}

	/**
	 * Asigna el estatus de usuario
	 * @param estatus
	 */
	public void setEstatus(EstatusGenerico estatus) {
		this.estatus = estatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((establecimiento == null) ? 0 : establecimiento.hashCode());
		result = prime * result + ((estatus == null) ? 0 : estatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (establecimiento == null) {
			if (other.establecimiento != null)
				return false;
		} else if (!establecimiento.equals(other.establecimiento))
			return false;
		if (estatus != other.estatus)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}	
	
}
