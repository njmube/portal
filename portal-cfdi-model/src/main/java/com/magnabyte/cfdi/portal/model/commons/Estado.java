package com.magnabyte.cfdi.portal.model.commons;

/**
 * Clase que representa un estado.
 * 
 * @author Edgar Pérez
 * 
 */
public class Estado {

	private Integer id;
	private String nombre;
	private Pais pais;
	
	/**
	 * Constructor por default
	 */
	public Estado() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}
