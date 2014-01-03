package com.magnabyte.cfdi.portal.model.cliente.enumeration;

/**
 * Clase que representa la enumeracion de TipoCliente
 * 
 * @author Edgar Pérez
 *
 */
public class TipoPersona {
	
	public static final Integer PERSONA_FISICA = 1;
	public static final Integer PERSONA_MORAL = 2;	
	
	/**
	 * Constructor por default
	 */
	public TipoPersona() {		
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
