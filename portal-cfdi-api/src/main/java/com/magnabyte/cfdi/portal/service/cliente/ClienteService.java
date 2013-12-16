package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;

public interface ClienteService {

	List<Cliente> findClientesByNameRfc(Cliente cliente);
	
	public Cliente read(Cliente cliente);

}
