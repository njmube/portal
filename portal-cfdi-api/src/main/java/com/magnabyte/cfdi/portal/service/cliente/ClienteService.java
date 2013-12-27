package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public interface ClienteService {
	
	public Cliente read(Cliente cliente);
	
	public boolean exist(Cliente cliente);
	
	public void save(Cliente cliente);
	
	public void update(Cliente cliente);
	
	List<Cliente> findClientesByNameRfc(Cliente cliente);

	Cliente readClientesByNameRfc(Cliente cliente);
}
