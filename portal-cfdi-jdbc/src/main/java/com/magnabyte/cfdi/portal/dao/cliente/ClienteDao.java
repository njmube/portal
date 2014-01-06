package com.magnabyte.cfdi.portal.dao.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public interface ClienteDao {

	List<Cliente> getAll();
	
	List<Cliente> findClientesByNameRfc(Cliente cliente);
	
	Cliente readClientesByNameRfc(Cliente cliente);
	
	Cliente read(Cliente cliente);		
	
	void save(Cliente cliente);

	void update(Cliente cliente);

	Cliente findClienteByRfc(Cliente cliente);


}
