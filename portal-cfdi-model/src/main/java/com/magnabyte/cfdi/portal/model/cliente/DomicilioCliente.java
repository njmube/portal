package com.magnabyte.cfdi.portal.model.cliente;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.magnabyte.cfdi.portal.model.commons.Domicilio;

public class DomicilioCliente extends Domicilio {

	@Valid
	@NotNull
	private Cliente cliente;
	
	/**
	 * Constructor por default
	 */
	public DomicilioCliente() {
		super();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
