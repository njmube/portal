package com.magnabyte.cfdi.portal.model.cliente;

import com.magnabyte.cfdi.portal.model.commons.Domicilio;
import com.magnabyte.cfdi.portal.model.commons.enumeration.EstatusDomiciolioCliente;

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
	private EstatusDomiciolioCliente estatus;
	
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
	public EstatusDomiciolioCliente getEstatus() {
		return estatus;
	}

	/**
	 * Asigna el estatus del domicilio
	 * @param estatus
	 */
	public void setEstatus(EstatusDomiciolioCliente estatus) {
		this.estatus = estatus;
	}
}
