package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;

public interface ClienteService {
	
	Cliente read(Cliente cliente);
	
	boolean exist(Cliente cliente);
	
	void save(Cliente cliente);
	
	void update(Cliente cliente);
	
	List<Cliente> findClientesByNameRfc(Cliente cliente);

	List<Cliente> getAll();
	
	Cliente readClientesByNameRfc(Cliente cliente);

	boolean comparaDirecciones(DomicilioCliente domicilio,
			DomicilioCliente domicilioBD);

	void saveClienteCorporativo(Cliente cliente);

	Cliente findClienteByRfc(Cliente cliente);

}
