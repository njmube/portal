package com.magnabyte.cfdi.portal.model.cliente;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.magnabyte.cfdi.portal.model.cliente.enumeration.TipoPersona;

/**
 * Clase que representa un cliente
 * 
 * @author Edgar Pérez
 * 
 */
public class Cliente {
	
	private Integer id;
	
	@NotEmpty
	@Pattern(regexp = "[A-Z,Ñ,&amp;]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?")
	private String rfc;
	@NotEmpty
	private String nombre;
	@NotEmpty
	@Valid
	private List<DomicilioCliente> domicilios;
	private TipoPersona tipoPersona;
	private Integer ventasMostrador;
	
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

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoCliente) {
		this.tipoPersona = tipoCliente;
	}

	public Integer getVentasMostrador() {
		return ventasMostrador;
	}

	public void setVentasMostrador(Integer ventasMostrador) {
		this.ventasMostrador = ventasMostrador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((rfc == null) ? 0 : rfc.hashCode());
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
