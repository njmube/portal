package com.magnabyte.cfdi.portal.model.commons;

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
	private int id;
	
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
	 * Obtiene el id de usuario
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asigna el id de usuario
	 * @param id
	 */
	public void setId(int id) {
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
	
	
}
