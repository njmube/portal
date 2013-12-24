package com.magnabyte.cfdi.portal.model.emisor;

import mx.gob.sat.cfd._3.Comprobante.Emisor;

public class EmpresaEmisor {

	private Integer id;
	private Emisor emisor;
	private String telefono;
	private String email;

	public EmpresaEmisor() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Emisor getEmisor() {
		return emisor;
	}

	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}

	@Override
	public String toString() {
		return "EmpresaEmisor [id=" + id + ", emisor=" + emisor + ", telefono="
				+ telefono + ", email=" + email + "]";
	}

}
