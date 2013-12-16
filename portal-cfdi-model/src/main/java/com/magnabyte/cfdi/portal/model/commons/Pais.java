package com.magnabyte.cfdi.portal.model.commons;

/**
 * Clase que representa un país.
 * 
 * @author Edgar Pérez
 * 
 */
public class Pais {

	private Integer id;
	private String nombre;

	/**
	 * Constructor por default
	 */
	public Pais() {

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

}
