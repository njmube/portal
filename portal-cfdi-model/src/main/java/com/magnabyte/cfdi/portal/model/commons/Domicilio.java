package com.magnabyte.cfdi.portal.model.commons;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase abstracta que representa el domicilio generico
 * 
 * @author Edgar Pérez
 *
 */
public abstract class Domicilio {

	protected Integer id;
	@NotEmpty
	@Length(max = 100)
	protected String calle;
	@NotEmpty
	@Length(max = 150)
	protected String colonia;
	@NotEmpty
	@Length(max = 100)
	protected String municipio;
	@NotNull
	protected Estado estado;
	@NotEmpty
	@Length(min = 4, max = 5)
	protected String codigoPostal;
	@Length(max = 100)
	protected String localidad;
	@Length(max = 200)
	protected String referencia;
	@NotEmpty
	@Length(min = 1, max = 20)
	protected String noExterior;
	@Length(max = 4)
	protected String noInterior;

	/**
	 * Costructor por default
	 */
	public Domicilio() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNoExterior() {
		return noExterior;
	}

	public void setNoExterior(String noExterior) {
		this.noExterior = noExterior;
	}

	public String getNoInterior() {
		return noInterior;
	}

	public void setNoInterior(String noInterior) {
		this.noInterior = noInterior;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Domicilio other = (Domicilio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Domicilio [id=");
		builder.append(id);
		builder.append(", calle=");
		builder.append(calle);
		builder.append(", colonia=");
		builder.append(colonia);
		builder.append(", municipio=");
		builder.append(municipio);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", codigoPostal=");
		builder.append(codigoPostal);
		builder.append(", localidad=");
		builder.append(localidad);
		builder.append(", referencia=");
		builder.append(referencia);
		builder.append(", noExterior=");
		builder.append(noExterior);
		builder.append(", noInterior=");
		builder.append(noInterior);
		builder.append("]");
		return builder.toString();
	}
	

}
