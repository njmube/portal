package com.magnabyte.cfdi.portal.model.cliente;

import java.util.List;

/**
 * Clase que representa un cliente
 * 
 * @author Edgar Pérez
 * 
 */
public class Cliente {

	private Integer id;
	private String rfc;
	private String nombre;
	private List<DomicilioCliente> domicilios;
	
	/**
	 * Constructos por default
	 */
	public Cliente() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DomicilioCliente> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(List<DomicilioCliente> domicilios) {
		this.domicilios = domicilios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [id=");
		builder.append(id);
		builder.append(", rfc=");
		builder.append(rfc);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", domicilios=");
		builder.append(domicilios);
		builder.append("]");
		return builder.toString();
	}

}
