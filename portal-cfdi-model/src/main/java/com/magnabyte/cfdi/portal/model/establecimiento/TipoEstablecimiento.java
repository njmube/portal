package com.magnabyte.cfdi.portal.model.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * 
 * Clase que representa el catalogo de tipo establecimiento
 */
public class TipoEstablecimiento extends OpcionDeCatalogo {

	/**
	 * Rol de establecimiento
	 */
	private String rol;
	
	/**
	 * Constructor por default
	 */
	public TipoEstablecimiento() {
		super();
	}

	/**
	 * Devuelve el rol de establecimiento
	 * @return rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Asigna el rol de establecimiento
	 * @param rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
}
