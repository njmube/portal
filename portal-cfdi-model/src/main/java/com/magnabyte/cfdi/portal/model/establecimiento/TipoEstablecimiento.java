package com.magnabyte.cfdi.portal.model.establecimiento;

import com.magnabyte.cfdi.portal.model.commons.OpcionDeCatalogo;

public class TipoEstablecimiento extends OpcionDeCatalogo {

	private String rol;
	
	/**
	 * Constructor por default
	 */
	public TipoEstablecimiento() {
		super();
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}
