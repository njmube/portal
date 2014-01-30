package com.magnabyte.cfdi.portal.model.cliente;

import com.magnabyte.cfdi.portal.model.commons.Domicilio;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusDomicilioCliente;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el domicilio de cliente
 */
public class DomicilioCliente extends Domicilio {

	/**
	 * Cliente del domicilio
	 */
	private Cliente cliente;
	
	/**
	 * Estatus del domicilio
	 */
	private EstatusDomicilioCliente estatus;
	
	/**
	 * Constructor por default
	 */
	public DomicilioCliente() {
	}

	/**
	 * Obtiene el cliente del domicilio
	 * @return cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Asigna el cliente del domicilio
	 * @param cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Obtiene el estatus del domicilio
	 * @return estatus
	 */
	public EstatusDomicilioCliente getEstatus() {
		return estatus;
	}

	/**
	 * Asigna el estatus del domicilio
	 * @param estatus
	 */
	public void setEstatus(EstatusDomicilioCliente estatus) {
		this.estatus = estatus;
	}
}
