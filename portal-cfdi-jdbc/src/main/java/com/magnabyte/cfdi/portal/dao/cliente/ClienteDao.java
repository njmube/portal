package com.magnabyte.cfdi.portal.dao.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Interfáz que representa el acceso a datos de cliente
 */
public interface ClienteDao {

	List<Cliente> getAll();
	
	List<Cliente> findClientesByNameRfc(Cliente cliente);
	
	Cliente readClientesByNameRfc(Cliente cliente);
	
	Cliente read(Cliente cliente);		
	
	void save(Cliente cliente);

	void update(Cliente cliente);

	Cliente findClienteByRfc(Cliente cliente);

	Cliente getClienteByNombre(Cliente cliente);


}
