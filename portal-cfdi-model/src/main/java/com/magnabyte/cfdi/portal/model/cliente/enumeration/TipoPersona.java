package com.magnabyte.cfdi.portal.model.cliente.enumeration;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa la enummeracion de tipo cliente
 */
public class TipoPersona {
	
	public static final Integer PERSONA_FISICA = 1;
	public static final Integer PERSONA_MORAL = 2;	
	
	/**
	 * Constructor por default
	 */
	public TipoPersona() {		
	}
	
	public TipoPersona(int id) {
		super();
		this.id = id;
	}

	/**
	 * Identificador unico de tipo de cliente
	 */
	int id;
	/**
	 * Nombre de tipo de cliente
	 */
	String nombre;

	/**
	 * Devuelve el identificador unuco de tipo de cliente
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asigna el identificador unico al tipo de cliente
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el nombre de tipo de cliente
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre de tipo de cliente
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
