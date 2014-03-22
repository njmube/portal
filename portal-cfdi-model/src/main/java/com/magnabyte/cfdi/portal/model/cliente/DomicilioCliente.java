package com.magnabyte.cfdi.portal.model.cliente;

import com.magnabyte.cfdi.portal.model.commons.Domicilio;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusGenerico;

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
	private EstatusGenerico estatus;
	
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
	public EstatusGenerico getEstatus() {
		return estatus;
	}

	/**
	 * Asigna el estatus del domicilio
	 * @param estatus
	 */
	public void setEstatus(EstatusGenerico estatus) {
		this.estatus = estatus;
	}
}
