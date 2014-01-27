package com.magnabyte.cfdi.portal.model.cliente.comparator;

import java.util.Comparator;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que representa el comparador de cliente por rfc
 */
public class ComparadorRfc implements Comparator<Cliente> {

	@Override
	public int compare(Cliente cliente1, Cliente cliente2) {
		return cliente1.getRfc().compareToIgnoreCase(cliente2.getRfc());
	}
	
}
