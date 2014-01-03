package com.magnabyte.cfdi.portal.model.cliente.comparator;

import java.util.Comparator;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public class ComparadorRfc implements Comparator<Cliente> {

	@Override
	public int compare(Cliente cliente1, Cliente cliente2) {
		return cliente1.getRfc().compareToIgnoreCase(cliente2.getRfc());
	}
	
}
