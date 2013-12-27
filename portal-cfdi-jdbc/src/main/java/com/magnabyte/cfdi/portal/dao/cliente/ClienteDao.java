package com.magnabyte.cfdi.portal.dao.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public interface ClienteDao {

	List<Cliente> findClientesByNameRfc(Cliente cliente);
	
	Cliente readClientesByNameRfc(Cliente cliente);
	
	public Cliente read(Cliente cliente);		
	
	public void save(Cliente cliente);

	public void update(Cliente cliente);

}
