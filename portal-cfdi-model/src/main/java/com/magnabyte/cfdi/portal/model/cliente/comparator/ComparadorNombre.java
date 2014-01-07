package com.magnabyte.cfdi.portal.model.cliente.comparator;

import java.util.Comparator;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public class ComparadorNombre implements Comparator<Cliente> {

	@Override
	public int compare(Cliente cliente1, Cliente cliente2) {
		return cliente1.getNombre().compareToIgnoreCase(cliente2.getNombre());
	}
	
}
