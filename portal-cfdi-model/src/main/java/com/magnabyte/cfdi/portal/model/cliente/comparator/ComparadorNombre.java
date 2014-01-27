package com.magnabyte.cfdi.portal.model.cliente.comparator;

import java.util.Comparator;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Clase que reperesenta el comparador de cliente por nombre
 */
public class ComparadorNombre implements Comparator<Cliente> {

	@Override
	public int compare(Cliente cliente1, Cliente cliente2) {
		return cliente1.getNombre().compareToIgnoreCase(cliente2.getNombre());
	}
	
}
