package com.magnabyte.cfdi.portal.model.emisor;

import com.magnabyte.cfdi.portal.model.cfdi.v32.Comprobante.Emisor;


/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa a una empresa de emisor
 */
public class EmpresaEmisor {

	/**
	 * Identificador único de emperesa emisor
	 */
	private Integer id;
	
	/**
	 * Emisor de empresa
	 */
	private Emisor emisor;
	
	/**
	 * Teléfono de la empresa
	 */
	private String telefono;
	
	/**
	 * Correo electrónico de la empresa
	 */
	private String email;

	/**
	 * Constructor por defecto de la empresa
	 */
	public EmpresaEmisor() {
	}

	/**
	 * Devuelde el identificador único 
	 * de empresa
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Asigna el identificador único de empresa
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Devuelve el telefóno de empresa
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Asigna el teléfono de la empresa
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Devuelve el email de empresa
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Asigna el emal de la empresa
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Decuelve el emisor de la empresa
	 * @return emisor
	 */
	public Emisor getEmisor() {
		return emisor;
	}

	/**
	 * Asigna el emisor de la empresa
	 * @param emisor
	 */
	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}

	@Override
	public String toString() {
		return "EmpresaEmisor [id=" + id + ", emisor=" + emisor + ", telefono="
				+ telefono + ", email=" + email + "]";
	}

}
