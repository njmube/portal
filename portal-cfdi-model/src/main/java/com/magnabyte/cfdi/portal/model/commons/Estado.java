package com.magnabyte.cfdi.portal.model.commons;

import javax.validation.constraints.NotNull;

/**
 * Clase que representa un estado.
 * 
 * @author Edgar Pérez
 * 
 */
public class Estado extends OpcionDeCatalogo {

	@NotNull
	private Pais pais;
	
	/**
	 * Constructor por default
	 */
	public Estado() {
		super();
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
}
