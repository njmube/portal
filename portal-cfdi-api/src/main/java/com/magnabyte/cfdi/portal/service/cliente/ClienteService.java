package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;

/**
 * 
 * @author Magnabyte, S.A. de C.V
 * magnabyte.com.mx
 * Fecha:27/01/2014
 * Intefáz que representa el servicio de domicilio de cliente
 */
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
	
	Cliente findClienteByName(Cliente cliente);

}
