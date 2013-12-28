package com.magnabyte.cfdi.portal.service.cliente;

import java.util.List;

import com.magnabyte.cfdi.portal.model.cliente.Cliente;
import com.magnabyte.cfdi.portal.model.cliente.DomicilioCliente;
import com.magnabyte.cfdi.portal.model.commons.Estado;

public interface DomicilioClienteService {

	List<DomicilioCliente>  getByCliente(Cliente cliente);
	
	public void save(Cliente cliente);
	
	public void update(Cliente cliente);
	
	public Estado readEstado(Estado estado);
	
}
