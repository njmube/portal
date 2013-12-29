package com.magnabyte.cfdi.portal.model.commons;


/**
 * Clase que representa una opción de catalogo
 * 
 * @author Edgar Pérez
 * 
 */
public class OpcionDeCatalogo {

	/**
	 * Identificador de la opción de catalogo
	 */
	private Integer id;
	
	/**
	 * Nombre de la opción de catalogo
	 */	
	private String nombre;

	/**
	 * Constructor por default
	 */
	public OpcionDeCatalogo() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		OpcionDeCatalogo other = (OpcionDeCatalogo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OpcionDeCatalogo [id=" + id + ", nombre=" + nombre + "]";
	}
}
