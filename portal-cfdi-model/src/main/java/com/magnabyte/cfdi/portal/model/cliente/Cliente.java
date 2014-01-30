package com.magnabyte.cfdi.portal.model.cliente;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.magnabyte.cfdi.portal.model.cliente.enumeration.TipoPersona;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa a un cliente
 */
public class Cliente {
	
	/**
	 * Identificador único de cliente
	 */
	private Integer id;
	
	/**
	 * Registro Federal de Contribuyentes de cliente
	 */
	@NotEmpty
	@Pattern(regexp = "[A-Z,Ñ,&amp;]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?")
	private String rfc;
	
	/**
	 * Nombre de cliente
	 */
	@NotEmpty
	private String nombre;
	
	/**
	 * Lista de domicilios de cliente
	 */
	@NotEmpty
	@Valid
	private List<DomicilioCliente> domicilios;
	
	/**
	 * Tipo de persona de clinte
	 */
	private TipoPersona tipoPersona;
	
	/**
	 * Email de cliente
	 */
	private String email;
	
	/**
	 * Constructos por default
	 */
	public Cliente() {

	}

	/**
	 * Devuelve el identificador unico de cliente
	 * del cliente
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Asigna el identificador único de clinete
	 * al cliente
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Devuelve el Registro Federal de Contribuyentes
	 * del cliente
	 * @return rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * Asigna el Registro Federal de Contribuyentes
	 * al cliente
	 * @param rfc
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * Devuelve el el nombre de cliente
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre al cliente
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la lista de comicilios de cliente
	 * @return List<{@link DomicilioCliente}> 
	 */
	public List<DomicilioCliente> getDomicilios() {
		return domicilios;
	}

	/**
	 * Asigna una lista de domicilios al cliente
	 * @param domicilios
	 */
	public void setDomicilios(List<DomicilioCliente> domicilios) {
		this.domicilios = domicilios;
	}

	/**
	 * Devuelve el tipo persona de cliente
	 * @return {@link TipoPersona}
	 */
	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * Devuelve el email de cliente
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Asigna el email de cliente
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Asigna el tipo perdona al cliente
	 * @param tipoCliente
	 */
	public void setTipoPersona(TipoPersona tipoCliente) {
		this.tipoPersona = tipoCliente;
	}

	/**
	 * Sobreescritura al método hashCode 
	 * por nombre y rfc de cliente 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((rfc == null) ? 0 : rfc.hashCode());
		return result;
	}

	/**
	 * Sobreescritura al método equals 
	 * por nombre y rfc de cliente
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (rfc == null) {
			if (other.rfc != null)
				return false;
		} else if (!rfc.equals(other.rfc))
			return false;
		return true;
	}

	/**
	 * Sobreescritura al método toString correspondinete 
	 * a los atrubutos de cliente
	 */
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
