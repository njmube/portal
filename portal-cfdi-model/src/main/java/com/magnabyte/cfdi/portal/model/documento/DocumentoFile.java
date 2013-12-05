package com.magnabyte.cfdi.portal.model.documento;

import java.io.Serializable;

public class DocumentoFile implements Serializable {

	private String folio;
	private String nombre;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
