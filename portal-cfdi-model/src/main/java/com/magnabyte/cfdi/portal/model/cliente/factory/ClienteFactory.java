package com.magnabyte.cfdi.portal.model.cliente.factory;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public class ClienteFactory {

	/**
	 * Metodo que retorna una indancia vacia de cliente
	 * 
	 * @return new Cliente
	 */
	public static Cliente newInstance() {
		return new Cliente();
	}
	
	/**
	 * Metodo que regresa una instanca vacia de cliente con
	 * identificador
	 * 
	 * @param int id
	 * @return new Cliente
	 */
	public static Cliente newInstance(int id){
		Cliente cliente = newInstance();
		cliente.setId(id);
		
		return cliente;
	}
	
	/**
	 * Metodo que regresa una instanca vacia de cliente con
	 * identificador
	 * 
	 * @param String id
	 * @return new Cliente
	 */
	public static Cliente newInstance(String id){
		return newInstance(Integer.parseInt(id));
	}
}
