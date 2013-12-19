package com.magnabyte.cfdi.portal.model.commons;

/**
 * Clase que representa un estado.
 * 
 * @author Edgar Pérez
 * 
 */
public class Estado extends OpcionDeCatalogo {
	
	private Pais pais;
	
	/**
	 * Constructor por default
	 */
	public Estado() {
		
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}
